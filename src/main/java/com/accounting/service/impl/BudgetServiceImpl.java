package com.accounting.service.impl;

import com.accounting.dto.BudgetRequest;
import com.accounting.dto.BudgetResponse;
import com.accounting.entity.Budget;
import com.accounting.mapper.BillMapper;
import com.accounting.mapper.BudgetMapper;
import com.accounting.service.BudgetService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BudgetServiceImpl implements BudgetService {

    @Autowired
    private BudgetMapper budgetMapper;

    @Autowired
    private BillMapper billMapper;

    @Override
    @Transactional
    public void createBudget(Long userId, BudgetRequest request) {
        // 检查是否已存在相同的预算
        QueryWrapper<Budget> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                .eq("year", request.getYear())
                .eq("month", request.getMonth())
                .eq("deleted", 0);
        
        if (request.getCategoryId() != null) {
            queryWrapper.eq("category_id", request.getCategoryId());
        } else {
            queryWrapper.isNull("category_id");
        }
        
        Budget existingBudget = budgetMapper.selectOne(queryWrapper);
        if (existingBudget != null) {
            throw new RuntimeException("该时间段的预算已存在");
        }
        
        Budget budget = new Budget();
        BeanUtils.copyProperties(request, budget);
        budget.setUserId(userId);
        budget.setStatus(request.getStatus() != null ? request.getStatus() : 1);
        budget.setCreateTime(LocalDateTime.now());
        budget.setUpdateTime(LocalDateTime.now());
        budget.setDeleted(0);
        
        budgetMapper.insert(budget);
    }

    @Override
    @Transactional
    public void updateBudget(Long userId, Long budgetId, BudgetRequest request) {
        Budget budget = budgetMapper.selectById(budgetId);
        if (budget == null || budget.getDeleted() == 1) {
            throw new RuntimeException("预算不存在");
        }
        
        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("无权限操作此预算");
        }
        
        BeanUtils.copyProperties(request, budget);
        budget.setUpdateTime(LocalDateTime.now());
        
        budgetMapper.updateById(budget);
    }

    @Override
    @Transactional
    public void deleteBudget(Long userId, Long budgetId) {
        Budget budget = budgetMapper.selectById(budgetId);
        if (budget == null || budget.getDeleted() == 1) {
            throw new RuntimeException("预算不存在");
        }
        
        if (!budget.getUserId().equals(userId)) {
            throw new RuntimeException("无权限操作此预算");
        }
        
        budget.setDeleted(1);
        budget.setUpdateTime(LocalDateTime.now());
        
        budgetMapper.updateById(budget);
    }

    @Override
    public BudgetResponse getBudgetDetail(Long userId, Long budgetId) {
        BudgetResponse response = budgetMapper.selectBudgetDetail(userId, budgetId);
        if (response == null) {
            throw new RuntimeException("预算不存在");
        }
        
        // 计算已使用金额和使用率
        calculateBudgetUsage(response);
        
        return response;
    }

    @Override
    public List<BudgetResponse> getBudgetList(Long userId, Integer year, Integer month) {
        List<BudgetResponse> budgets = budgetMapper.selectBudgetList(userId, year, month);
        
        // 为每个预算计算使用情况
        budgets.forEach(this::calculateBudgetUsage);
        
        return budgets;
    }
    
    private void calculateBudgetUsage(BudgetResponse budget) {
        // 计算已使用金额（支出类型）
        BigDecimal usedAmount = billMapper.sumAmountByUserAndDate(
                budget.getCategoryId() != null ? budget.getCategoryId() : null,
                budget.getYear(),
                budget.getMonth(),
                2 // 支出类型
        );
        
        if (usedAmount == null) {
            usedAmount = BigDecimal.ZERO;
        }
        
        budget.setUsedAmount(usedAmount);
        budget.setRemainingAmount(budget.getAmount().subtract(usedAmount));
        
        // 计算使用率
        if (budget.getAmount().compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal percentage = usedAmount
                    .divide(budget.getAmount(), 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
            budget.setUsagePercentage(percentage);
        } else {
            budget.setUsagePercentage(BigDecimal.ZERO);
        }
    }
}
