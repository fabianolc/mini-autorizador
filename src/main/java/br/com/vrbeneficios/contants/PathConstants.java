package br.com.vrbeneficios.contants;

import lombok.experimental.UtilityClass;

/**
 * The type Path constants.
 */
@UtilityClass
public class PathConstants {
    /**
     * The constant CARTOES_PATH.
     */
    public static final String CARTOES_PATH = "cartoes";
    /**
     * The constant CARTOES_NUMERO_PATH.
     */
    public static final String CARTOES_NUMERO_PATH = CARTOES_PATH + "/{numeroCartao}";

    /**
     * The constant TRANSACOES_PATH.
     */
    public static final String TRANSACOES_PATH = "transacoes";
}
