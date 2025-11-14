package com.accounting.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class StatisticResponse {

    /** 总收入 */
    private BigDecimal totalIncome;

    /** 总支出 */
    private BigDecimal totalExpense;

    /** 结余（收入-支出） */
    private BigDecimal balance;

    /** 统计区间开始日期 */
    private String startDate;

    /** 统计区间结束日期 */
    private String endDate;
}
