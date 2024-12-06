package br.com.vrbeneficios.controller.impl;

import br.com.vrbeneficios.controller.CartaoController;
import br.com.vrbeneficios.domain.mapper.CartaoMapper;
import br.com.vrbeneficios.domain.request.CartaoRequest;
import br.com.vrbeneficios.domain.response.CartaoResponse;
import br.com.vrbeneficios.facade.CartaoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Cartao controller.
 */
@RestController
@RequiredArgsConstructor
public class CartaoControllerImpl implements CartaoController {
    private final CartaoFacade cartaoFacade;
    private final CartaoMapper cartaoMapper;

    @Override
    public CartaoResponse cadastrarCartao(CartaoRequest cartaoRequest) {
        return cartaoMapper.dtoToResponse(cartaoFacade.cadastrarCartao(cartaoMapper.requestToDto(cartaoRequest)));
    }

    @Override
    public String obterSaldoCartao(String numeroCartao) {
        return cartaoFacade.obterSaldoCartao(numeroCartao);
    }
}
