package br.com.vrbeneficios.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import lombok.Builder;

/**
 * The type Movimento cartao request.
 */
@Builder
public record MovimentoCartaoRequest(
        @Pattern(regexp = "\\d{16}") @NotBlank String numeroCartao,
        @NotBlank String senha,
        @NotNull BigDecimal valor) {}
