package br.com.vrbeneficios.domain.exception;

/**
 * The type Cartao inexistente exception.
 */
public class CartaoInexistenteException extends RuntimeException {
    /**
     * Instantiates a new Cartao inexistente exception.
     */
    public CartaoInexistenteException() {
        super("CARTAO_INEXISTENTE");
    }
}
