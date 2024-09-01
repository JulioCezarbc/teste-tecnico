package com.julio.tgid.domain;

import com.julio.tgid.domain.enumerated.TransactionType;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tb_transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private BigDecimal systemFee;

    @Column(nullable = false)
    private BigDecimal finalAmount;

    @Column(nullable = false)
    private BigDecimal taxApplied;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    private TransactionType type;

    @Column(nullable = false)
    private Date timestamp;

    public Transaction(){}

    public Transaction(UUID id, Client client, Company company, BigDecimal amount,
                       BigDecimal systemFee, BigDecimal finalAmount, BigDecimal taxApplied, TransactionType type, Date timestamp) {
        this.id = id;
        this.client = client;
        this.company = company;
        this.amount = amount;
        this.systemFee = systemFee;
        this.finalAmount = finalAmount;
        this.taxApplied = taxApplied;
        this.type = type;
        this.timestamp = timestamp;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getSystemFee() {
        return systemFee;
    }

    public void setSystemFee(BigDecimal systemFee) {
        this.systemFee = systemFee;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public BigDecimal getTaxApplied() {
        return taxApplied;
    }

    public void setTaxApplied(BigDecimal taxApplied) {
        this.taxApplied = taxApplied;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
