package br.com.vrbeneficios.config;

import br.com.vrbeneficios.domain.exception.CartaoInexistenteException;
import br.com.vrbeneficios.domain.exception.NotFoundException;
import br.com.vrbeneficios.domain.exception.RegistroExistenteException;
import br.com.vrbeneficios.domain.exception.SaldoInsuficienteException;
import br.com.vrbeneficios.domain.exception.SenhaInvalidaException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Custom exception handler.
 */
@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Registro existente exception handle response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(RegistroExistenteException.class)
    public ResponseEntity<Object> registroExistenteExceptionHandle(RegistroExistenteException exception) {
        return ResponseEntity.unprocessableEntity().body(exception.getCartaoResponse());
    }

    /**
     * Cartao inexistente exception handle response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(CartaoInexistenteException.class)
    public ResponseEntity<String> cartaoInexistenteExceptionHandle(CartaoInexistenteException exception) {
        return ResponseEntity.unprocessableEntity().body(exception.getMessage());
    }

    /**
     * Saldo insuficiente exception handle response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<String> saldoInsuficienteExceptionHandle(SaldoInsuficienteException exception) {
        return ResponseEntity.unprocessableEntity().body(exception.getMessage());
    }

    /**
     * Senha invalida exception handle response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(SenhaInvalidaException.class)
    public ResponseEntity<String> senhaInvalidaExceptionHandle(SenhaInvalidaException exception) {
        return ResponseEntity.unprocessableEntity().body(exception.getMessage());
    }

    /**
     * Not found exception handle response entity.
     *
     * @return the response entity
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Void> notFoundExceptionHandle() {
        return ResponseEntity.notFound().build();
    }
}
