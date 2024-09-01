package com.julio.tgid.DTO;

import java.math.BigDecimal;

public record ClientDTO(String name, String cpf, String email, BigDecimal balance) {
}
