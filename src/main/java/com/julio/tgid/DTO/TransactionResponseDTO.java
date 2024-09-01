package com.julio.tgid.DTO;

import com.julio.tgid.domain.enumerated.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record TransactionResponseDTO(String cpf, String nameClient, String cnpj, String nameCompany, BigDecimal amount, BigDecimal systemFee, BigDecimal finalAmount, TransactionType type,
                                     LocalDateTime timestamp) {
}
