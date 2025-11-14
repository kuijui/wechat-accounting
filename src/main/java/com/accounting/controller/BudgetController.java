package com.accounting.controller;

import com.accounting.common.Result;
import com.accounting.dto.BudgetRequest;
import com.accounting.dto.BudgetResponse;
import com.accounting.service.BudgetService;
import com.accounting.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/budget")
@Validated
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("未授权");
        }
        token = token.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token无效");
        }
        return jwtUtil.getUserIdFromToken(token);
    }

    @PostMapping("/create")
    public Result<Void> createBudget(@RequestBody @Validated BudgetRequest request,
                                     HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            budgetService.createBudget(userId, request);
            return Result.success();
        } catch (Exception e) {
            log.error("创建预算失败", e);
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Result<Void> updateBudget(@PathVariable Long id,
                                     @RequestBody @Validated BudgetRequest request,
                                     HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            budgetService.updateBudget(userId, id, request);
            return Result.success();
        } catch (Exception e) {
            log.error("更新预算失败", e);
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<Void> deleteBudget(@PathVariable Long id,
                                     HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            budgetService.deleteBudget(userId, id);
            return Result.success();
        } catch (Exception e) {
            log.error("删除预算失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public Result<BudgetResponse> getBudgetDetail(@PathVariable Long id,
                                                  HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            BudgetResponse response = budgetService.getBudgetDetail(userId, id);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取预算详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<BudgetResponse>> getBudgetList(@RequestParam(required = false) Integer year,
                                                      @RequestParam(required = false) Integer month,
                                                      HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            List<BudgetResponse> response = budgetService.getBudgetList(userId, year, month);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取预算列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
