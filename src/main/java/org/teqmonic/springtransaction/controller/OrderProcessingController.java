package org.teqmonic.springtransaction.controller;

import lombok.RequiredArgsConstructor;
import org.teqmonic.springtransaction.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teqmonic.springtransaction.service.OrderProcessingService;
import org.teqmonic.springtransaction.service.ProductService;
import org.teqmonic.springtransaction.service.isolation.ReadUncommittedDemo;
import org.teqmonic.springtransaction.service.isolation.RepeatableReadDemo;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController {

    private final OrderProcessingService orderProcessingService;
    private final ReadUncommittedDemo readUncommittedDemo;
    private final RepeatableReadDemo repeatableReadDemo;

    /**
     * API to place an order
     *
     * @param order the order details
     * @return the processed order with updated total price
     */
    @PostMapping
    public ResponseEntity<?> placeAnOrder(@RequestBody Order order) {
        return ResponseEntity.ok(orderProcessingService.placeAnOrder(order));
    }

    @GetMapping("/isolation/product_id/{productId}/quantity/{quantity}")
    public void testIsolation(int productId, int quantity) {
        readUncommittedDemo.simulateTransaction(productId, quantity);
       // repeatableReadDemo.simulateTransaction(productId, quantity);
    }

}
