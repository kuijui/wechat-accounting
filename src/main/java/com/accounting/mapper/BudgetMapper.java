package com.accounting.mapper;

import com.accounting.dto.BudgetResponse;
import com.accounting.entity.Budget;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BudgetMapper extends BaseMapper<Budget> {

    List<BudgetResponse> selectBudgetList(@Param("userId") Long userId,
                                          @Param("year") Integer year,
                                          @Param("month") Integer month);

    BudgetResponse selectBudgetDetail(@Param("userId") Long userId,
                                      @Param("budgetId") Long budgetId);
}
