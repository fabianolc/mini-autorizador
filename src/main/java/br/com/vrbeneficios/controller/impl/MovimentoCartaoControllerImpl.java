package br.com.vrbeneficios.controller.impl;

import br.com.vrbeneficios.controller.MovimentoCartaoController;
import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import br.com.vrbeneficios.facade.CartaoFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Movimento cartao controller.
 */
@RestController
@RequiredArgsConstructor
public class MovimentoCartaoControllerImpl implements MovimentoCartaoController {

    private final CartaoFacade cartaoFacade;

    @Override
    public void efetuarTransacao(MovimentoCartaoRequest movimentoCartaoRequest) {
        cartaoFacade.efetuarMovimento(movimentoCartaoRequest);
    }
}
