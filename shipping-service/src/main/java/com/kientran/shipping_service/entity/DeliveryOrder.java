package com.kientran.shipping_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "delivery_order")
public class DeliveryOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "receive_date")
    private String receiveDate;

    @Column(name = "complete_date")
    private String completeDate;

    @Column(name = "image_confirmation")
    private String imageConfirmation;

    @Column(name = "is_completed")
    private Boolean isCompleted;

    @JsonIgnore
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "shipper_id")
    private Shipper shipper;

}
