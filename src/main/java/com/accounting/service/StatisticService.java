package com.accounting.service;

import com.accounting.dto.CategoryStatisticResponse;
import com.accounting.dto.StatisticResponse;
import com.accounting.dto.TrendResponse;

import java.time.LocalDate;
import java.util.List;

public interface StatisticService {

    StatisticResponse getOverviewStatistics(Long userId, LocalDate startDate, LocalDate endDate);

    List<CategoryStatisticResponse> getCategoryStatistics(Long userId, Integer type, LocalDate startDate, LocalDate endDate);

    List<TrendResponse> getTrendStatistics(Long userId, LocalDate startDate, LocalDate endDate);
}
