package com.kientran.order_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "delevery_information")
public class DeliveryInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String recipientName;

    private String phoneNumber;

    private String commune;

    private String district;

    private String province;

    private String description;

    @JsonIgnore
    @OneToOne(mappedBy = "deliveryInformation")
    private Order order;
}
