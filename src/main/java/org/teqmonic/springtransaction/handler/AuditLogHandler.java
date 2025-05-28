package org.teqmonic.springtransaction.handler;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.teqmonic.springtransaction.entity.AuditLog;
import org.teqmonic.springtransaction.entity.Order;
import org.teqmonic.springtransaction.repo.AuditLogRepository;

import java.time.LocalDateTime;

@Component
public class AuditLogHandler {

    @Autowired
    private AuditLogRepository auditLogRepository;

    // Log audit details (runs in an independent transaction)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logAuditDetails(Order order, String action) {
        AuditLog auditLog = new AuditLog();
        auditLog.setOrderId(Long.valueOf(order.getId()));
        auditLog.setAction(action);
        auditLog.setTimeStamp(LocalDateTime.now());
        auditLogRepository.save(auditLog);
    }
}
