package com.julio.tgid.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.br.CNPJ;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_companies")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true, length = 14,nullable = false)
    @CNPJ
    private String cnpj;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    @Positive
    private BigDecimal systemFee;
    @Column(nullable = false)
    @Positive
    private BigDecimal balance;

    public Company(){}

    public Company(UUID id, String cnpj, String name, BigDecimal systemFee, BigDecimal balance) {
        this.id = id;
        this.cnpj = cnpj;
        this.name = name;
        this.systemFee = systemFee;
        this.balance = balance;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @Positive BigDecimal getSystemFee() {
        return systemFee;
    }

    public void setSystemFee(@Positive BigDecimal systemFee) {
        this.systemFee = systemFee;
    }

    public @Positive BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(@Positive BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
