package org.teqmonic.springtransaction.handler;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.teqmonic.springtransaction.entity.Product;
import org.teqmonic.springtransaction.repo.InventoryRepository;

@Service
public class InventoryHandler {

    private final InventoryRepository inventoryRepository;

    public InventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Product updateProductDetails(Product product) {

        //forcefully throwing exception to simulate use of tx
        if(product.getPrice() > 5000){
            throw new RuntimeException("DB crashed.....");
        }
        System.out.println("Before updating inventory.");
        return inventoryRepository.save(product);
    }


    public Product getProduct(int id) {
        return inventoryRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException("Product not available with id : " + id)
                );
    }
}
