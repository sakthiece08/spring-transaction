package org.teqmonic.springtransaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log")
@AllArgsConstructor
@NoArgsConstructor
public class AuditLog {

    @Id
    @SequenceGenerator(name = "audit_id_seq_gen", sequenceName = "audit_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "audit_id_seq_gen")
    private Long id;

    private Long orderId;  // The order associated with the log

    private String action;  // Action taken (e.g., "Order Placed", "Payment Failed")

    private LocalDateTime timeStamp;  // Timestamp of the action

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }
}
