package com.accounting.controller;

import com.accounting.common.Result;
import com.accounting.dto.CategoryStatisticResponse;
import com.accounting.dto.StatisticResponse;
import com.accounting.dto.TrendResponse;
import com.accounting.service.StatisticService;
import com.accounting.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/statistic")
public class StatisticController {

    @Autowired
    private StatisticService statisticService;

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

    @GetMapping("/overview")
    public Result<StatisticResponse> getOverviewStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            StatisticResponse response = statisticService.getOverviewStatistics(userId, startDate, endDate);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取概览统计失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/category")
    public Result<List<CategoryStatisticResponse>> getCategoryStatistics(
            @RequestParam Integer type,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            List<CategoryStatisticResponse> response = statisticService.getCategoryStatistics(userId, type, startDate, endDate);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取分类统计失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/trend")
    public Result<List<TrendResponse>> getTrendStatistics(
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            HttpServletRequest request) {
        try {
            Long userId = getUserIdFromRequest(request);
            List<TrendResponse> response = statisticService.getTrendStatistics(userId, startDate, endDate);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取趋势统计失败", e);
            return Result.error(e.getMessage());
        }
    }
}
