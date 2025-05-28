package org.teqmonic.springtransaction.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "user_order")
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @SequenceGenerator(name = "order_id_seq_gen", sequenceName = "order_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_seq_gen")
    private int id;
    private int productId;
    private int quantity;
    private String status;
    private double totalPrice;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
