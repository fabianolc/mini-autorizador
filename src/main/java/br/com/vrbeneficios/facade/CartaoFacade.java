package br.com.vrbeneficios.facade;

import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.CREDITO;
import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.DEBITO;
import static java.util.Optional.of;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;
import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import br.com.vrbeneficios.domain.exception.SaldoInsuficienteException;
import br.com.vrbeneficios.domain.exception.SenhaInvalidaException;
import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import br.com.vrbeneficios.service.CartaoService;
import br.com.vrbeneficios.service.MovimentoCartaoService;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Cartao facade.
 */
@Service
@RequiredArgsConstructor
public class CartaoFacade {

    private final CartaoService cartaoService;

    private final MovimentoCartaoService movimentoCartaoService;

    /**
     * Cadastrar cartao cartao dto.
     *
     * @param cartaoDto the cartao dto
     * @return the cartao dto
     */
    public CartaoDto cadastrarCartao(CartaoDto cartaoDto) {
        Cartao cartaoSaved = cartaoService.cadastrarCartao(cartaoDto);
        movimentoCartaoService.efetuarMovimentoCartao(MovimentoCartao.builder()
                .numeroCartao(cartaoSaved.getNumeroCartao())
                .dataMovimento(LocalDate.now())
                .valor(new BigDecimal("500.00"))
                .tipoMovimento(CREDITO)
                .build());
        cartaoSaved.setSaldo(movimentoCartaoService.calcularSaldoCartao(cartaoSaved.getNumeroCartao()));
        return cartaoService.atualizarCartao(cartaoSaved);
    }

    /**
     * Efetuar movimento.
     *
     * @param movimentoCartaoRequest the movimento cartao request
     */
    public void efetuarMovimento(MovimentoCartaoRequest movimentoCartaoRequest) {
        Cartao cartaoSaved = cartaoService.findById(movimentoCartaoRequest.numeroCartao());
        validarMovimento(cartaoSaved, movimentoCartaoRequest);
        movimentoCartaoService.efetuarMovimentoCartao(MovimentoCartao.builder()
                .numeroCartao(cartaoSaved.getNumeroCartao())
                .dataMovimento(LocalDate.now())
                .valor(movimentoCartaoRequest.valor())
                .tipoMovimento(DEBITO)
                .build());
        cartaoSaved.setSaldo(movimentoCartaoService.calcularSaldoCartao(cartaoSaved.getNumeroCartao()));
        cartaoService.atualizarCartao(cartaoSaved);
    }

    /**
     * Obter saldo cartao string.
     *
     * @param numeroCartao the numero cartao
     * @return the string
     */
    public String obterSaldoCartao(String numeroCartao) {
        return cartaoService.obterSaldoCartao(numeroCartao);
    }

    private void validarMovimento(Cartao cartao, MovimentoCartaoRequest movimentoCartaoRequest) {
        validaSenha(cartao, movimentoCartaoRequest);
        validaSaldoDisponivel(cartao, movimentoCartaoRequest);
    }

    private void validaSenha(Cartao cartao, MovimentoCartaoRequest movimentoCartaoRequest) {
        of(cartao)
                .filter(c -> c.getSenha().equalsIgnoreCase(movimentoCartaoRequest.senha()))
                .orElseThrow(SenhaInvalidaException::new);
    }

    private void validaSaldoDisponivel(Cartao cartao, MovimentoCartaoRequest movimentoCartaoRequest) {
        of(cartao)
                .filter(c -> c.getSaldo().compareTo(movimentoCartaoRequest.valor()) >= 0)
                .orElseThrow(SaldoInsuficienteException::new);
    }
}
