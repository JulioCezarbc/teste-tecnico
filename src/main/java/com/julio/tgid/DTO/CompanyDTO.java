package com.julio.tgid.DTO;

import java.math.BigDecimal;

public record CompanyDTO(String cnpj, String name, BigDecimal systemFee, BigDecimal balance) {
}
