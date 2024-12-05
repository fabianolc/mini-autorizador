package br.com.vrbeneficios.domain.exception;

/**
 * The type Saldo insuficiente exception.
 */
public class SaldoInsuficienteException extends RuntimeException {
    /**
     * Instantiates a new Saldo insuficiente exception.
     */
    public SaldoInsuficienteException() {
        super("SALDO_INSUFICIENTE");
    }
}
