package br.com.vrbeneficios.service;

import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import java.math.BigDecimal;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Movimento cartao service.
 */
public interface MovimentoCartaoService {
    /**
     * Efetuar movimento cartao movimento cartao.
     *
     * @param movimentoCartaoDto the movimento cartao dto
     * @return the movimento cartao
     */
    MovimentoCartao efetuarMovimentoCartao(MovimentoCartao movimentoCartaoDto);

    /**
     * Calcular saldo cartao big decimal.
     *
     * @param numeroCartao the numero cartao
     * @return the big decimal
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    BigDecimal calcularSaldoCartao(String numeroCartao);
}
