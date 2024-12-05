package br.com.vrbeneficios.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;
import br.com.vrbeneficios.domain.exception.CartaoInexistenteException;
import br.com.vrbeneficios.domain.exception.NotFoundException;
import br.com.vrbeneficios.domain.exception.RegistroExistenteException;
import br.com.vrbeneficios.domain.mapper.CartaoMapper;
import br.com.vrbeneficios.repository.CartaoRepository;
import br.com.vrbeneficios.service.impl.CartaoServiceImpl;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * The type Cartao service impl test.
 */
@ExtendWith(MockitoExtension.class)
class CartaoServiceImplTest {

    @InjectMocks
    private CartaoServiceImpl cartaoService;

    @Mock
    private CartaoRepository cartaoRepository;

    @Spy
    private CartaoMapper cartaoMapper = Mappers.getMapper(CartaoMapper.class);

    /**
     * Cadastrar cartao teste sucesso.
     */
    @Test
    void cadastrarCartaoTeste_Sucesso() {

        CartaoDto cartaoDto = CartaoDto.builder()
                .numeroCartao("1234123412341234")
                .senha("1234")
                .build();
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        when(cartaoRepository.saveAndFlush(any(Cartao.class)))
                .thenReturn(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .build());

        Cartao cartao = cartaoService.cadastrarCartao(cartaoDto);

        assertNotNull(cartao);
        assertEquals("1234123412341234", cartao.getNumeroCartao());
        assertEquals("1234", cartao.getSenha());
        assertNull(cartao.getSaldo());

        verify(cartaoRepository, times(1)).findById(anyString());
        verify(cartaoRepository, times(1)).saveAndFlush(any(Cartao.class));
    }

    /**
     * Cadastrar cartao teste registro existente.
     */
    @Test
    void cadastrarCartaoTeste_RegistroExistente() {
        CartaoDto cartaoDto = CartaoDto.builder()
                .numeroCartao("1234123412341234")
                .senha("1234")
                .build();
        when(cartaoRepository.findById(anyString()))
                .thenReturn(Optional.of(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .build()));
        assertThrows(RegistroExistenteException.class, () -> cartaoService.cadastrarCartao(cartaoDto));
        verify(cartaoRepository, times(1)).findById(anyString());
        verify(cartaoRepository, never()).saveAndFlush(any(Cartao.class));
    }

    /**
     * Find by id teste sucesso.
     */
    @Test
    void findByIdTeste_Sucesso() {
        when(cartaoRepository.findById(anyString()))
                .thenReturn(Optional.of(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .build()));
        Cartao cartao = cartaoService.findById("1234123412341234");
        assertNotNull(cartao);
        assertEquals("1234123412341234", cartao.getNumeroCartao());
        assertEquals("1234", cartao.getSenha());
        verify(cartaoRepository, times(1)).findById(anyString());
    }

    /**
     * Find by id teste cartao inexistente.
     */
    @Test
    void findByIdTeste_CartaoInexistente() {
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(CartaoInexistenteException.class, () -> cartaoService.findById("1234123412341234"));
        verify(cartaoRepository, times(1)).findById(anyString());
    }

    /**
     * Atualizar cartao teste sucesso.
     */
    @Test
    void atualizarCartaoTeste_Sucesso() {
        when(cartaoRepository.saveAndFlush(any(Cartao.class)))
                .thenReturn(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .build());
        CartaoDto cartao = cartaoService.atualizarCartao(
                Cartao.builder().numeroCartao("1234123412341234").senha("1234").build());
        assertNotNull(cartao);
        assertEquals("1234123412341234", cartao.numeroCartao());
        assertEquals("1234", cartao.senha());
        verify(cartaoRepository, times(1)).saveAndFlush(any(Cartao.class));
    }

    /**
     * Obter saldo cartao teste sucesso.
     */
    @Test
    void obterSaldoCartaoTeste_Sucesso() {
        when(cartaoRepository.findById(anyString()))
                .thenReturn(Optional.of(Cartao.builder()
                        .numeroCartao("1234123412341234")
                        .senha("1234")
                        .saldo(new BigDecimal("5000.00"))
                        .build()));
        String saldo = cartaoService.obterSaldoCartao("1234123412341234");
        assertNotNull(saldo);
        assertEquals("5000.00", saldo);
        verify(cartaoRepository, times(1)).findById(anyString());
    }

    /**
     * Obter saldo cartao teste not found.
     */
    @Test
    void obterSaldoCartaoTeste_NotFound() {
        when(cartaoRepository.findById(anyString())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> cartaoService.obterSaldoCartao("1234123412341234"));
        verify(cartaoRepository, times(1)).findById(anyString());
    }
}
