package com.wsousa.cqrspattern.service.impl;

import com.wsousa.cqrspattern.entity.PurchaseOrder;
import com.wsousa.cqrspattern.repository.ProductRepository;
import com.wsousa.cqrspattern.repository.PurchaseOrderRepository;
import com.wsousa.cqrspattern.repository.UserRepository;
import com.wsousa.cqrspattern.service.OrderCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;

@Service
public class OrderCommandServiceImpl implements OrderCommandService {

    private static final long ORDER_CANCELLATION_WINDOW = 30;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PurchaseOrderRepository purchaseOrderRepository;

    @Override
    public PurchaseOrder createOrder(Long userIndex, Long productIndex) {
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        purchaseOrder.setProductId(this.getProductById(productIndex));
        purchaseOrder.setUserId(getUsersById(userIndex));
        return this.purchaseOrderRepository.save(purchaseOrder);
    }

    private Long getUsersById(Long userIndex){
        return this.userRepository.getOne(userIndex).getId();
    }

    private Long getProductById(Long productIndex){
        return this.productRepository.getOne(productIndex).getId();
    }


    @Override
    public void cancelOrder(long orderId) {
        this.purchaseOrderRepository.findById(orderId)
                .ifPresent(purchaseOrder -> {
                    LocalDate expiryDate = LocalDate.now().plusDays(1);
                    long daysUntilExpiry = Duration.between(LocalDate.now().atStartOfDay(), expiryDate.atStartOfDay()).toDays();
                    if(daysUntilExpiry <= ORDER_CANCELLATION_WINDOW){
                        this.purchaseOrderRepository.deleteById(orderId);
                        //additional logic to issue refund
                    }
                });
    }
}
