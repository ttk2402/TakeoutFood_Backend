package com.kientran.shipping_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "shipper")
public class Shipper {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "credential_code")
    private String credentialCode;

    @Column(name = "amount")
    private Double amount;

    @JsonBackReference
    @OneToMany(mappedBy = "shipper", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeliveryOrder> deliveryOrders;

    @OneToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
