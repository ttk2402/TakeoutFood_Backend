package com.kientran.order_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer accountId;

    private Double totalprice;

    private String date;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "checkout_id")
    private Checkout checkout;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "order_status_id")
    private OrderStatus orderStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_information_id")
    private DeliveryInformation deliveryInformation;

    @JsonBackReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ItemOrdered> itemOrdereds;

    @OneToOne
    @JoinColumn(name = "bill_id")
    private Bill bill;
}
