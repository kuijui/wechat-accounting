package com.accounting.mapper;

import com.accounting.dto.BillQueryRequest;
import com.accounting.dto.BillResponse;
import com.accounting.entity.Bill;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BillMapper extends BaseMapper<Bill> {

    List<BillResponse> selectBillList(@Param("userId") Long userId, @Param("query") BillQueryRequest queryRequest);

    BillResponse selectBillDetail(@Param("userId") Long userId, @Param("billId") Long billId);

    BigDecimal sumAmountByUserAndDate(@Param("userId") Long userId,
                                      @Param("categoryId") Long categoryId,
                                      @Param("year") Integer year,
                                      @Param("month") Integer month,
                                      @Param("type") Integer type);
}
