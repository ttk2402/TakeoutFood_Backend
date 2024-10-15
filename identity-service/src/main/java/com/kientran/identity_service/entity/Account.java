package com.kientran.identity_service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "account")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String username;

    private String firstname;

    private String lastname;

    private String password;

    private String email;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "account_status_id")
    private AccountStatus account_status;
}