package org.teqmonic.springtransaction.handler;

import org.teqmonic.springtransaction.entity.Order;
import org.teqmonic.springtransaction.repo.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderHandler {

    private final OrderRepository orderRepository;

    public OrderHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Order saveOrder(Order order){
        return orderRepository.save(order);
    }
}
