package br.com.vrbeneficios.domain.dto;

import java.math.BigDecimal;
import lombok.Builder;

/**
 * The type Cartao dto.
 */
@Builder
public record CartaoDto(String numeroCartao, String senha, BigDecimal saldo) {}
