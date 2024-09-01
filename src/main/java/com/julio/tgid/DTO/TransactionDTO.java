package com.julio.tgid.DTO;

import com.julio.tgid.domain.enumerated.TransactionType;

import java.math.BigDecimal;

public record TransactionDTO(String cpf, String cnpj, BigDecimal amount, TransactionType type) {
}
