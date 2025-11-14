package com.accounting.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TrendResponse {

    private String period;

    private BigDecimal income;

    private BigDecimal expense;

    private BigDecimal balance;
}
