package com.wsousa.cqrspattern.service;

import com.wsousa.cqrspattern.entity.PurchaseOrder;

public interface OrderCommandService {
    PurchaseOrder createOrder(Long userIndex, Long productIndex);
    void cancelOrder(long orderId);
}
