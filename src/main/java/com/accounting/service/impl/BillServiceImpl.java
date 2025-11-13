package com.accounting.service.impl;

import com.accounting.dto.BillQueryRequest;
import com.accounting.dto.BillRequest;
import com.accounting.dto.BillResponse;
import com.accounting.entity.Bill;
import com.accounting.mapper.BillMapper;
import com.accounting.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BillResponse createBill(Long userId, BillRequest request) {
        Bill bill = new Bill();
        bill.setUserId(userId);
        bill.setCategoryId(request.getCategoryId());
        bill.setAmount(request.getAmount());
        bill.setType(request.getType());
        bill.setBillDate(request.getBillDate());
        bill.setDescription(StringUtils.hasText(request.getDescription()) ? request.getDescription() : null);
        bill.setRemark(StringUtils.hasText(request.getRemark()) ? request.getRemark() : null);
        billMapper.insert(bill);
        return billMapper.selectBillDetail(userId, bill.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BillResponse updateBill(Long userId, Long billId, BillRequest request) {
        Bill existing = billMapper.selectById(billId);
        if (existing == null || !existing.getUserId().equals(userId)) {
            throw new RuntimeException("账单不存在");
        }
        existing.setCategoryId(request.getCategoryId());
        existing.setAmount(request.getAmount());
        existing.setType(request.getType());
        existing.setBillDate(request.getBillDate());
        existing.setDescription(StringUtils.hasText(request.getDescription()) ? request.getDescription() : null);
        existing.setRemark(StringUtils.hasText(request.getRemark()) ? request.getRemark() : null);
        billMapper.updateById(existing);
        return billMapper.selectBillDetail(userId, billId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteBill(Long userId, Long billId) {
        Bill existing = billMapper.selectById(billId);
        if (existing == null || !existing.getUserId().equals(userId)) {
            throw new RuntimeException("账单不存在");
        }
        return billMapper.deleteById(billId) > 0;
    }

    @Override
    public BillResponse getBillDetail(Long userId, Long billId) {
        BillResponse response = billMapper.selectBillDetail(userId, billId);
        if (response == null) {
            throw new RuntimeException("账单不存在");
        }
        return response;
    }

    @Override
    public List<BillResponse> listBills(Long userId, BillQueryRequest queryRequest) {
        List<BillResponse> list = billMapper.selectBillList(userId, queryRequest);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.emptyList();
        }
        return list;
    }
}
