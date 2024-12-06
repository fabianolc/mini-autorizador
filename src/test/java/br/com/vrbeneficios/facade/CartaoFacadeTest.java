package br.com.vrbeneficios.facade;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;
import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import br.com.vrbeneficios.domain.exception.SaldoInsuficienteException;
import br.com.vrbeneficios.domain.exception.SenhaInvalidaException;
import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import br.com.vrbeneficios.service.CartaoService;
import br.com.vrbeneficios.service.MovimentoCartaoService;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Cartao facade test.
 */
@ExtendWith(MockitoExtension.class)
class CartaoFacadeTest {

    @InjectMocks
    private CartaoFacade cartaoFacade;

    @Mock
    private CartaoService cartaoService;

    @Mock
    private MovimentoCartaoService movimentoCartaoService;

    /**
     * Cadastrar cartao teste sucesso.
     */
    @Test
    void cadastrarCartaoTeste_Sucesso() {
        when(cartaoService.cadastrarCartao(any(CartaoDto.class)))
                .thenReturn(Cartao.builder().numeroCartao("1234123412341234").build());
        when(movimentoCartaoService.calcularSaldoCartao(anyString())).thenReturn(new BigDecimal("100.00"));
        when(cartaoService.atualizarCartao(any(Cartao.class)))
                .thenReturn(CartaoDto.builder()
                        .numeroCartao("1234123412341234")
                        .saldo(new BigDecimal("100.00"))
                        .build());

        CartaoDto cartaoDto = cartaoFacade.cadastrarCartao(CartaoDto.builder()
                .numeroCartao("1234123412341234")
                .senha("1234")
                .build());

        assertNotNull(cartaoDto);
        assertEquals(new BigDecimal("100.00"), cartaoDto.saldo());

        verify(cartaoService).cadastrarCartao(any(CartaoDto.class));
        verify(movimentoCartaoService).efetuarMovimentoCartao(any(MovimentoCartao.class));
        verify(movimentoCartaoService).calcularSaldoCartao(anyString());
        verify(cartaoService).atualizarCartao(any(Cartao.class));
    }

    /**
     * Efetuar movimento teste sucesso.
     */
    @Test
    void efetuarMovimentoTeste_Sucesso() {
        when(cartaoService.findById(anyString()))
                .thenReturn(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .saldo(new BigDecimal("200.00"))
                        .build());
        when(movimentoCartaoService.calcularSaldoCartao(anyString())).thenReturn(new BigDecimal("100.00"));
        when(cartaoService.atualizarCartao(any(Cartao.class)))
                .thenReturn(CartaoDto.builder()
                        .numeroCartao("1234123412341234")
                        .saldo(new BigDecimal("100.00"))
                        .build());
        cartaoFacade.efetuarMovimento(MovimentoCartaoRequest.builder()
                .numeroCartao("1234123412341234")
                .senha("1234")
                .valor(new BigDecimal("100.00"))
                .build());
        verify(cartaoService).findById(anyString());
        verify(movimentoCartaoService).efetuarMovimentoCartao(any(MovimentoCartao.class));
        verify(movimentoCartaoService).calcularSaldoCartao(anyString());
        verify(cartaoService).atualizarCartao(any(Cartao.class));
    }

    /**
     * Efetuar movimento teste senha invalida.
     */
    @Test
    void efetuarMovimentoTeste_SenhaInvalida() {
        when(cartaoService.findById(anyString()))
                .thenReturn(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .saldo(new BigDecimal("200.00"))
                        .build());
        Assertions.assertThrows(
                SenhaInvalidaException.class,
                () -> cartaoFacade.efetuarMovimento(MovimentoCartaoRequest.builder()
                        .numeroCartao("1234123412341234")
                        .senha("12345")
                        .valor(new BigDecimal("100.00"))
                        .build()));
        verify(cartaoService).findById(anyString());
        verify(movimentoCartaoService, never()).efetuarMovimentoCartao(any(MovimentoCartao.class));
        verify(movimentoCartaoService, never()).calcularSaldoCartao(anyString());
        verify(cartaoService, never()).atualizarCartao(any(Cartao.class));
    }

    /**
     * Efetuar movimento teste saldo insuficiente.
     */
    @Test
    void efetuarMovimentoTeste_SaldoInsuficiente() {
        when(cartaoService.findById(anyString()))
                .thenReturn(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .saldo(new BigDecimal("200.00"))
                        .build());
        Assertions.assertThrows(
                SaldoInsuficienteException.class,
                () -> cartaoFacade.efetuarMovimento(MovimentoCartaoRequest.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .valor(new BigDecimal("300.00"))
                        .build()));
        verify(cartaoService).findById(anyString());
        verify(movimentoCartaoService, never()).efetuarMovimentoCartao(any(MovimentoCartao.class));
        verify(movimentoCartaoService, never()).calcularSaldoCartao(anyString());
        verify(cartaoService, never()).atualizarCartao(any(Cartao.class));
    }
}
