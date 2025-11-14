package com.accounting.service.impl;

import com.accounting.dto.CategoryStatisticResponse;
import com.accounting.dto.StatisticResponse;
import com.accounting.dto.TrendResponse;
import com.accounting.mapper.BillMapper;
import com.accounting.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@Service
public class StatisticServiceImpl implements StatisticService {

    @Autowired
    private BillMapper billMapper;

    @Override
    public StatisticResponse getOverviewStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        BigDecimal totalIncome = billMapper.sumAmountByDateRange(userId, 1, startDate, endDate);
        BigDecimal totalExpense = billMapper.sumAmountByDateRange(userId, 2, startDate, endDate);
        
        if (totalIncome == null) totalIncome = BigDecimal.ZERO;
        if (totalExpense == null) totalExpense = BigDecimal.ZERO;
        
        StatisticResponse response = new StatisticResponse();
        response.setTotalIncome(totalIncome);
        response.setTotalExpense(totalExpense);
        response.setBalance(totalIncome.subtract(totalExpense));
        response.setStartDate(startDate.toString());
        response.setEndDate(endDate.toString());
        
        return response;
    }

    @Override
    public List<CategoryStatisticResponse> getCategoryStatistics(Long userId, Integer type, LocalDate startDate, LocalDate endDate) {
        List<CategoryStatisticResponse> statistics = billMapper.selectCategoryStatistics(userId, type, startDate, endDate);
        
        BigDecimal total = statistics.stream()
                .map(CategoryStatisticResponse::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        
        if (total.compareTo(BigDecimal.ZERO) > 0) {
            statistics.forEach(stat -> {
                BigDecimal percentage = stat.getTotalAmount()
                        .divide(total, 4, RoundingMode.HALF_UP)
                        .multiply(new BigDecimal("100"));
                stat.setPercentage(percentage);
            });
        }
        
        return statistics;
    }

    @Override
    public List<TrendResponse> getTrendStatistics(Long userId, LocalDate startDate, LocalDate endDate) {
        return billMapper.selectTrendStatistics(userId, startDate, endDate);
    }
}
