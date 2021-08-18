package com.wsousa.cqrspattern.controller.command;

import com.wsousa.cqrspattern.dto.OrderCommandDto;
import com.wsousa.cqrspattern.entity.PurchaseOrder;
import com.wsousa.cqrspattern.service.OrderCommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@ConditionalOnProperty(name = "app.write.enabled", havingValue = "true")
public class OrderCommandController {

    @Autowired
    private OrderCommandService orderCommandService;

    @PostMapping("/sale")
    public ResponseEntity<PurchaseOrder> placeOrder(@RequestBody OrderCommandDto dto){
        PurchaseOrder purchaseOrder = this.orderCommandService.createOrder(dto.getUserIndex(), dto.getProductIndex());
        return ResponseEntity.ok().body(purchaseOrder);
    }

    @PutMapping("/cancel-order/{orderId}")
    public void cancelOrder(@PathVariable long orderId){
        this.orderCommandService.cancelOrder(orderId);
    }
}
