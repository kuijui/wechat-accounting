package com.accounting.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class BudgetResponse {

    private Long id;

    private Long categoryId;

    private String categoryName;

    private BigDecimal amount;

    private Integer year;

    private Integer month;

    private Integer status;

    private BigDecimal usedAmount;

    private BigDecimal remainingAmount;

    private BigDecimal usagePercentage;

    private LocalDateTime createTime;
}
