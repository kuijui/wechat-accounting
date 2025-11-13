package com.accounting.dto;

import lombok.Data;

@Data
public class CategoryResponse {

    private Long id;

    private String name;

    private String icon;

    private String color;

    private Integer type;

    private Integer sortOrder;

    private Boolean isSystem;
}
