package com.accounting.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CategoryStatisticResponse {

    private Long categoryId;

    private String categoryName;

    private String categoryIcon;

    private Integer type;

    private BigDecimal totalAmount;

    /**
     * 该分类下的账单数量
     */
    private Long billCount;

    private BigDecimal percentage;
}
