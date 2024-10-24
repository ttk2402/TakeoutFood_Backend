package com.kientran.shipping_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "number_card")
    private String numberCard;

    @Column(name = "card_holder_name")
    private String cardHolderName;

    @JsonIgnore
    @OneToOne(mappedBy = "payment")
    private Shipper shipper;
}
