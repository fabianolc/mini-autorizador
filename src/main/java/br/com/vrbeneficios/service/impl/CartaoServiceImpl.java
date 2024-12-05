package br.com.vrbeneficios.service.impl;

import br.com.vrbeneficios.domain.dto.CartaoDto;
import br.com.vrbeneficios.domain.entity.Cartao;
import br.com.vrbeneficios.domain.exception.CartaoInexistenteException;
import br.com.vrbeneficios.domain.exception.NotFoundException;
import br.com.vrbeneficios.domain.exception.RegistroExistenteException;
import br.com.vrbeneficios.domain.mapper.CartaoMapper;
import br.com.vrbeneficios.repository.CartaoRepository;
import br.com.vrbeneficios.service.CartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Cartao service.
 */
@Service
@RequiredArgsConstructor
public class CartaoServiceImpl implements CartaoService {
    private final CartaoRepository cartaoRepository;
    private final CartaoMapper cartaoMapper;

    @Override
    public Cartao cadastrarCartao(CartaoDto cartaoDto) {
        cartaoRepository.findById(cartaoDto.numeroCartao()).ifPresent(cartao -> {
            throw new RegistroExistenteException(cartaoMapper.dtoToResponse(cartaoDto));
        });
        return cartaoRepository.saveAndFlush(cartaoMapper.dtoToEntity(cartaoDto));
    }

    @Override
    public Cartao findById(String numeroCartao) {
        return cartaoRepository.findById(numeroCartao).orElseThrow(CartaoInexistenteException::new);
    }

    @Override
    public CartaoDto atualizarCartao(Cartao cartao) {
        return cartaoMapper.entityToDto(cartaoRepository.saveAndFlush(cartao));
    }

    @Override
    public String obterSaldoCartao(String numeroCartao) {
        return cartaoRepository
                .findById(numeroCartao)
                .orElseThrow(NotFoundException::new)
                .getSaldo()
                .toPlainString();
    }
}
