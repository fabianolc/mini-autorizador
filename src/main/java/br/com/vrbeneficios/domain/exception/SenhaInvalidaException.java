package br.com.vrbeneficios.domain.exception;

/**
 * The type Senha invalida exception.
 */
public class SenhaInvalidaException extends RuntimeException {
    /**
     * Instantiates a new Senha invalida exception.
     */
    public SenhaInvalidaException() {
        super("SENHA_INVALIDA");
    }
}
