package com.accounting.service;

import com.accounting.dto.BudgetRequest;
import com.accounting.dto.BudgetResponse;

import java.util.List;

public interface BudgetService {

    void createBudget(Long userId, BudgetRequest request);

    void updateBudget(Long userId, Long budgetId, BudgetRequest request);

    void deleteBudget(Long userId, Long budgetId);

    BudgetResponse getBudgetDetail(Long userId, Long budgetId);

    List<BudgetResponse> getBudgetList(Long userId, Integer year, Integer month);
}
