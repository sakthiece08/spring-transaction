package org.teqmonic.springtransaction.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.teqmonic.springtransaction.entity.Product;
import org.teqmonic.springtransaction.repo.InventoryRepository;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    private EntityManager entityManager;
    Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void updateInventory(int productId, int quantity) {
        logger.info("updateInventory for Product id {}", productId);
        Product product = inventoryRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        product.setQuantity(quantity);
        inventoryRepository.save(product);
        entityManager.flush(); // Ensure the update is sent to DB
        logger.info("Transaction A: updateInventory, transaction uncommitted, quantity {}", quantity);
        try { // Simulate a long-running transaction (does not commit yet)
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("Transaction A: updateInventory, transaction committed");
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public int fetchInventoryCount(int productId) {
        logger.info("fetchInventoryCount for Product id {}", productId);
        Product product = inventoryRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        logger.info("Transaction B: fetched inventory count {}", product.getQuantity());
        return product.getQuantity();
    }

    // Transaction B: Read stock multiple times
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public void fetchStock(int productId) {

        // First read
        Product product1 = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        logger.info("Transaction B: First read stock as " + product1.getQuantity());

        // Simulate a delay to allow Transaction A to update the stock
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Second read
        Product product2 = inventoryRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        logger.info("Transaction B: Second read stock as " + product2.getQuantity());
    }

}
