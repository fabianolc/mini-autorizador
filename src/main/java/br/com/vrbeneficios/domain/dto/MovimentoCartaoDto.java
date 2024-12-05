package br.com.vrbeneficios.domain.dto;

import br.com.vrbeneficios.domain.enums.TipoMovimentoEnum;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Builder;

/**
 * The type Movimento cartao dto.
 */
@Builder
public record MovimentoCartaoDto(
        Long id, String numeroCartao, TipoMovimentoEnum tipoMovimento, LocalDate dataMovimento, BigDecimal valor) {}
