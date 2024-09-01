package com.julio.tgid.DTO;

import java.math.BigDecimal;

public record ClientDTO(String firstName, String lastName, String cpf, String email, BigDecimal balance) {
}
