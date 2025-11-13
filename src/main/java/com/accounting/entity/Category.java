package com.accounting.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName("category")
public class Category {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("name")
    private String name;

    @TableField("icon")
    private String icon;

    @TableField("color")
    private String color;

    @TableField("type")
    private Integer type; // 1-收入 2-支出

    @TableField("sort_order")
    private Integer sortOrder;

    @TableField("is_system")
    private Integer isSystem; // 0-用户自定义 1-系统预设

    @TableField("user_id")
    private Long userId; // 用户自定义分类时不为空

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @TableLogic
    @TableField("deleted")
    private Integer deleted;
}