package br.com.vrbeneficios.domain.exception;

import br.com.vrbeneficios.domain.response.CartaoResponse;
import lombok.Getter;

/**
 * The type Registro existente exception.
 */
@Getter
public class RegistroExistenteException extends RuntimeException {

    private final CartaoResponse cartaoResponse;

    /**
     * Instantiates a new Registro existente exception.
     *
     * @param cartaoResponse the cartao response
     */
    public RegistroExistenteException(CartaoResponse cartaoResponse) {
        this.cartaoResponse = cartaoResponse;
    }
}
