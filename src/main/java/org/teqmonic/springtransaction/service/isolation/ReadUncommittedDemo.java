package org.teqmonic.springtransaction.service.isolation;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.teqmonic.springtransaction.service.ProductService;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class ReadUncommittedDemo {

    private final ProductService productService;
    Logger logger = LoggerFactory.getLogger(ReadUncommittedDemo.class);
    public void simulateTransaction(int productId, int quantity) {
        logger.info("In simulateTransaction");

        Thread threadA = new Thread(() -> {
            productService.updateInventory(productId, quantity);
        }, "ThreadA");

        Thread threadB = new Thread(() -> {
            try { // wait to allow ThreadA to start and hold the transaction
                Thread.sleep(2000);
                int fetchInventoryCount = productService.fetchInventoryCount(productId);
                logger.info("ThreadB fetched inventory count {}", fetchInventoryCount);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }, "ThreadB");

        threadA.start();
        threadB.start();

        try { // wait for the thread to complete
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }
}
