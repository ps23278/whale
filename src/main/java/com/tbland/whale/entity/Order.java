package com.tbland.whale.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private LocalDateTime orderDate;
    private String address;
    private String status;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
    @Column(precision=15,scale=2)
    private BigDecimal totalAmount;
    private String paymentMethod;

}
