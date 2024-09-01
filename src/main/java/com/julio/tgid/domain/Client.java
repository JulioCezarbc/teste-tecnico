package com.julio.tgid.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tb_users")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 20, nullable = false)
    private String firstName;
    @Column(length = 20, nullable = false)
    private String lastName;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    private BigDecimal saldo;


}
