package com.accounting.mapper;

import com.accounting.dto.CategoryResponse;
import com.accounting.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

    List<CategoryResponse> selectCategoryList(@Param("userId") Long userId, @Param("type") Integer type);
}
