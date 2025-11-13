package com.accounting.service;

import com.accounting.dto.BillQueryRequest;
import com.accounting.dto.BillRequest;
import com.accounting.dto.BillResponse;

import java.util.List;

public interface BillService {

    BillResponse createBill(Long userId, BillRequest request);

    BillResponse updateBill(Long userId, Long billId, BillRequest request);

    boolean deleteBill(Long userId, Long billId);

    BillResponse getBillDetail(Long userId, Long billId);

    List<BillResponse> listBills(Long userId, BillQueryRequest queryRequest);
}
