package org.teqmonic.springtransaction.controller;

import org.teqmonic.springtransaction.entity.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.teqmonic.springtransaction.service.OrderProcessingService;

@RestController
@RequestMapping("/api/orders")
public class OrderProcessingController {

    private final OrderProcessingService orderProcessingService;



    public OrderProcessingController(OrderProcessingService orderProcessingService){
        this.orderProcessingService = orderProcessingService;
    }

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

}
