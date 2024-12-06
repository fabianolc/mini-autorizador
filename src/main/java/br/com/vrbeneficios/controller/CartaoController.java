package br.com.vrbeneficios.controller;

import static br.com.vrbeneficios.contants.PathConstants.CARTOES_NUMERO_PATH;
import static br.com.vrbeneficios.contants.PathConstants.CARTOES_PATH;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.TEXT_PLAIN_VALUE;

import br.com.vrbeneficios.domain.request.CartaoRequest;
import br.com.vrbeneficios.domain.response.CartaoResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * The interface Cartao controller.
 */
@Tag(name = "CartaoController")
public interface CartaoController {

    /**
     * Cadastrar cartao cartao response.
     *
     * @param cartao the cartao
     * @return the cartao response
     */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = CARTOES_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    CartaoResponse cadastrarCartao(@Validated @RequestBody CartaoRequest cartao);

    /**
     * Obter saldo cartao string.
     *
     * @param numeroCartao the numero cartao
     * @return the string
     */
    @GetMapping(value = CARTOES_NUMERO_PATH, produces = TEXT_PLAIN_VALUE)
    String obterSaldoCartao(@PathVariable(name = "numeroCartao") String numeroCartao);
}
