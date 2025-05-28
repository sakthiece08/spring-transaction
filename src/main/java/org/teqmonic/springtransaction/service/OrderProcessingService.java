package org.teqmonic.springtransaction.service;

import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.teqmonic.springtransaction.entity.Order;
import org.teqmonic.springtransaction.entity.Product;
import org.teqmonic.springtransaction.handler.*;


@Service
public class OrderProcessingService {

    private final OrderHandler orderHandler;

    private final InventoryHandler inventoryHandler;

    private final AuditLogHandler auditLogHandler;

    private final PaymentValidatorHandler paymentValidatorHandler;

    private final NotificationHandler notificationHandler;

    private final ProductRecommendationHandler recommendationHandler;

    Logger logger = LoggerFactory.getLogger(OrderProcessingService.class);


    public OrderProcessingService(OrderHandler orderHandler,
                                  InventoryHandler inventoryHandler,
                                  AuditLogHandler auditLogHandler,
                                  PaymentValidatorHandler paymentValidatorHandler,
                                  NotificationHandler notificationHandler,
                                  ProductRecommendationHandler recommendationHandler) {
        this.orderHandler = orderHandler;
        this.inventoryHandler = inventoryHandler;
        this.auditLogHandler = auditLogHandler;
        this.paymentValidatorHandler = paymentValidatorHandler;
        this.notificationHandler = notificationHandler;
        this.recommendationHandler = recommendationHandler;
    }

    // REQUIRED : join an existing transaction or create a new one if not exist
    // REQUIRES_NEW : Always create a new transaction , suspending if any existing transaction
    // MANDATORY : Require an existing transaction , if nothing found it will throw exception
    // NEVER : Ensure the method will run without transaction , throw an exception if found any
    // NOT_SUPPORTED : Execute method without transaction, suspending any active transaction
    // SUPPORTS : Supports if there is any active transaction , if not execute without transaction
    // NESTED : Execute within a nested transaction, allowing nested transaction
    // to rollback independently if there is any exception without impacting outer transaction

    //outer tx
    // isolation : controls the visibility of changes made by one transaction to other transaction
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public Order placeAnOrder(Order order) {

        // get product inventory
        Product product = inventoryHandler.getProduct(order.getProductId());

        // validate stock availability <(5)
        validateStockAvailability(order, product);

        // update total price in order entity
        order.setTotalPrice(order.getQuantity() * product.getPrice());
        order.setStatus("PLACED");

        Order saveOrder = null;
        //try {
            //save order
            saveOrder = orderHandler.saveOrder(order);

            //update stock in inventory
            updateInventoryStock(order, product);
          //  auditLogHandler.logAuditDetails(order, "order placement succeeded");
       // } catch (Exception ex) {
         //   System.out.println(ex);
         //   auditLogHandler.logAuditDetails(order, "order placement failed");
       // }

        //retries 3
        //notificationHandler.sendOrderConfirmationNotification(order);

       //  paymentValidatorHandler.validatePayment(order);

        // recommendationHandler.getRecommendations();

//        getCustomerDetails();

        return saveOrder;
    }


    @Transactional(propagation = Propagation.SUPPORTS)
    public void getCustomerDetails() {
        System.out.println("Customer details fetched !!!!!");
    }

    // Call this method after placeAnOrder is successfully completed
    public void processOrder(Order order) {
        // Step 1: Place the order
        Order savedOrder = placeAnOrder(order);

        // Step 2: Send notification (non-transactional)
        notificationHandler.sendOrderConfirmationNotification(order);
    }

    private static void validateStockAvailability(Order order, Product product) {
        if (order.getQuantity() > product.getQuantity()) {
            throw new RuntimeException("Insufficient stock !");
        }
    }

    private void updateInventoryStock(Order order, Product product) {
        int availableStock = product.getQuantity() - order.getQuantity();
        product.setQuantity(availableStock);
        inventoryHandler.updateProductDetails(product);
    }


}
