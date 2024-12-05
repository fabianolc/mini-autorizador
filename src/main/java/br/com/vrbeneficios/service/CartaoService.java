package br.com.vrbeneficios.service;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;

/**
 * The interface Cartao service.
 */
public interface CartaoService {
    /**
     * Cadastrar cartao cartao.
     *
     * @param cartaoDto the cartao dto
     * @return the cartao
     */
    Cartao cadastrarCartao(CartaoDto cartaoDto);

    /**
     * Find by id cartao.
     *
     * @param numeroCartao the numero cartao
     * @return the cartao
     */
    Cartao findById(String numeroCartao);

    /**
     * Atualizar cartao cartao dto.
     *
     * @param cartao the cartao
     * @return the cartao dto
     */
    CartaoDto atualizarCartao(Cartao cartao);

    /**
     * Obter saldo cartao string.
     *
     * @param numeroCartao the numero cartao
     * @return the string
     */
    String obterSaldoCartao(String numeroCartao);
}
