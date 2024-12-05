package br.com.vrbeneficios.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * The type Cartao request.
 */
public record CartaoRequest(@Pattern(regexp = "\\d{16}") @NotBlank String numeroCartao, @NotBlank String senha) {}
