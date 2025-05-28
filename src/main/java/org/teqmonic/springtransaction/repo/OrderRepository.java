package org.teqmonic.springtransaction.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.teqmonic.springtransaction.entity.Order;

public interface OrderRepository extends JpaRepository<Order,Integer> {
}
