package br.com.vrbeneficios.controller;

import static br.com.vrbeneficios.contants.PathConstants.TRANSACOES_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import br.com.vrbeneficios.domain.request.MovimentoCartaoRequest;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The interface Movimento cartao controller.
 */
@Tag(name = "MovimentoCartaoController")
public interface MovimentoCartaoController {
    /**
     * Efetuar transacao.
     *
     * @param movimentoCartaoRequest the movimento cartao request
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = TRANSACOES_PATH, consumes = APPLICATION_JSON_VALUE)
    void efetuarTransacao(@Validated @RequestBody MovimentoCartaoRequest movimentoCartaoRequest);
}
