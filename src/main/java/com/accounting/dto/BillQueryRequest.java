package com.accounting.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BillQueryRequest {

    /** 开始日期（包含） */
    private LocalDate startDate;

    /** 结束日期（包含） */
    private LocalDate endDate;

    /** 类型：1-收入，2-支出 */
    private Integer type;

    /** 分类ID */
    private Long categoryId;
}
