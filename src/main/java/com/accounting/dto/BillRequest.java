package com.accounting.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BillRequest {

    @NotNull(message = "分类不能为空")
    @ApiModelProperty("分类ID")
    private Long categoryId;

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.01", message = "金额必须大于0")
    @ApiModelProperty("金额")
    private BigDecimal amount;

    @NotNull(message = "类型不能为空")
    @ApiModelProperty("类型：1-收入，2-支出")
    private Integer type;

    @NotNull(message = "日期不能为空")
    @ApiModelProperty("记账日期")
    private LocalDate billDate;

    @ApiModelProperty("描述")
    private String description;

    @ApiModelProperty("备注")
    private String remark;
}
