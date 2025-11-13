package com.accounting.controller;

import com.accounting.common.Result;
import com.accounting.dto.BillQueryRequest;
import com.accounting.dto.BillRequest;
import com.accounting.dto.BillResponse;
import com.accounting.service.BillService;
import com.accounting.utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bill")
@Validated
public class BillController {

    @Autowired
    private BillService billService;

    @Autowired
    private JwtUtil jwtUtil;

    private Long getUserIdFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            throw new RuntimeException("未授权");
        }
        token = token.substring(7);
        if (!jwtUtil.validateToken(token)) {
            throw new RuntimeException("Token无效");
        }
        return jwtUtil.getUserIdFromToken(token);
    }

    @PostMapping("/create")
    public Result<BillResponse> createBill(@Valid @RequestBody BillRequest request, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            BillResponse response = billService.createBill(userId, request);
            return Result.success("创建成功", response);
        } catch (Exception e) {
            log.error("创建账单失败", e);
            return Result.error(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public Result<BillResponse> updateBill(@PathVariable("id") Long billId,
                                           @Valid @RequestBody BillRequest request,
                                           HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            BillResponse response = billService.updateBill(userId, billId, request);
            return Result.success("更新成功", response);
        } catch (Exception e) {
            log.error("更新账单失败", e);
            return Result.error(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public Result<String> deleteBill(@PathVariable("id") Long billId, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            boolean success = billService.deleteBill(userId, billId);
            if (success) {
                return Result.success("删除成功");
            }
            return Result.error("删除失败");
        } catch (Exception e) {
            log.error("删除账单失败", e);
            return Result.error(e.getMessage());
        }
    }

    @GetMapping("/detail/{id}")
    public Result<BillResponse> getBillDetail(@PathVariable("id") Long billId, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            BillResponse response = billService.getBillDetail(userId, billId);
            return Result.success(response);
        } catch (Exception e) {
            log.error("获取账单详情失败", e);
            return Result.error(e.getMessage());
        }
    }

    @PostMapping("/list")
    public Result<List<BillResponse>> listBills(@RequestBody BillQueryRequest queryRequest, HttpServletRequest httpRequest) {
        try {
            Long userId = getUserIdFromRequest(httpRequest);
            List<BillResponse> list = billService.listBills(userId, queryRequest);
            return Result.success(list);
        } catch (Exception e) {
            log.error("查询账单列表失败", e);
            return Result.error(e.getMessage());
        }
    }
}
