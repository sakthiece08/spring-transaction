package org.teqmonic.springtransaction.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.teqmonic.springtransaction.entity.Product;

public interface InventoryRepository extends JpaRepository<Product,Integer> {
}
