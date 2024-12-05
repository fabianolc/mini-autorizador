package br.com.vrbeneficios.service;

import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.CREDITO;
import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.DEBITO;
import static java.math.BigDecimal.ZERO;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import br.com.vrbeneficios.domain.mapper.MovimentoCartaoMapper;
import br.com.vrbeneficios.repository.MovimentoCartaoRepository;
import br.com.vrbeneficios.service.impl.MovimentoCartaoServiceImpl;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Movimento cartao service impl test.
 */
@ExtendWith(MockitoExtension.class)
class MovimentoCartaoServiceImplTest {

    @InjectMocks
    private MovimentoCartaoServiceImpl movimentoCartaoService;

    @Mock
    private MovimentoCartaoRepository movimentoCartaoRepository;

    @Spy
    private MovimentoCartaoMapper movimentoCartaoMapper = Mappers.getMapper(MovimentoCartaoMapper.class);

    /**
     * Efetuar movimento cartao.
     */
    @Test
    void efetuarMovimentoCartao() {
        when(movimentoCartaoRepository.saveAndFlush(Mockito.any(MovimentoCartao.class)))
                .thenReturn(new MovimentoCartao());
        movimentoCartaoService.efetuarMovimentoCartao(
                MovimentoCartao.builder().tipoMovimento(CREDITO).build());
        verify(movimentoCartaoRepository, times(1)).saveAndFlush(Mockito.any(MovimentoCartao.class));
    }

    /**
     * Calcular saldo cartao test sem movimentos saldo zerado.
     */
    @Test
    void calcularSaldoCartaoTest_SemMovimentos_SaldoZerado() {
        when(movimentoCartaoRepository.findMovimentoCartaosByNumeroCartao(anyString()))
                .thenReturn(Collections.emptyList());
        BigDecimal saldo = movimentoCartaoService.calcularSaldoCartao("1234123412341234");
        assertEquals(ZERO, saldo);
    }

    /**
     * Calcular saldo cartao test sem movimentos saldo positivo.
     */
    @Test
    void calcularSaldoCartaoTest_SemMovimentos_SaldoPositivo() {
        when(movimentoCartaoRepository.findMovimentoCartaosByNumeroCartao(anyString()))
                .thenReturn(asList(
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(DEBITO)
                                .valor(new BigDecimal("100.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build()));
        BigDecimal saldo = movimentoCartaoService.calcularSaldoCartao("1234123412341234");
        assertEquals(new BigDecimal("200.00"), saldo);
    }

    /**
     * Calcular saldo cartao test sem movimentos saldo negativo positivo.
     */
    @Test
    void calcularSaldoCartaoTest_SemMovimentos_SaldoNegativoPositivo() {
        when(movimentoCartaoRepository.findMovimentoCartaosByNumeroCartao(anyString()))
                .thenReturn(asList(
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(DEBITO)
                                .valor(new BigDecimal("400.00"))
                                .build(),
                        MovimentoCartao.builder()
                                .tipoMovimento(CREDITO)
                                .valor(new BigDecimal("100.00"))
                                .build()));
        BigDecimal saldo = movimentoCartaoService.calcularSaldoCartao("1234123412341234");
        assertEquals(new BigDecimal("100.00").negate(), saldo);
    }
}
