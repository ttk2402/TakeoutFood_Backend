package com.kientran.order_service.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "checkout")
public class Checkout {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String method;

    @JsonBackReference
    @OneToMany(mappedBy = "checkout", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
