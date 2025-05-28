package org.teqmonic.springtransaction.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.teqmonic.springtransaction.entity.AuditLog;
import org.teqmonic.springtransaction.entity.Order;
import org.teqmonic.springtransaction.repo.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.teqmonic.springtransaction.service.OrderProcessingService;

import java.time.LocalDateTime;

@Service
public class PaymentValidatorHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;

    Logger logger = LoggerFactory.getLogger(PaymentValidatorHandler.class);

  @Transactional(propagation = Propagation.MANDATORY)
    public void validatePayment(Order order) {
      logger.info("In validatePayment for Order id {}", order.getId());
        // Assume payment processing happens here
        boolean paymentSuccessful = false;

        // If payment is unsuccessful, we log the payment failure in the mandatory transaction
        if (!paymentSuccessful) {
            AuditLog paymentFailureLog = new AuditLog();
            paymentFailureLog.setOrderId(Long.valueOf(order.getId()));
            paymentFailureLog.setAction("Payment Failed for Order");
            paymentFailureLog.setTimeStamp(LocalDateTime.now());

            // Save the payment failure log
            auditLogRepository.save(paymentFailureLog);
        }
        
    }

}
