package com.accounting.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BillResponse {

    private Long id;

    private Long categoryId;

    private String categoryName;

    private String categoryIcon;

    private Integer type;

    private BigDecimal amount;

    private LocalDate billDate;

    private String description;

    private String remark;

    private LocalDateTime createTime;
}
