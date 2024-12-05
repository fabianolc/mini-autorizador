package br.com.vrbeneficios.service.impl;

import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.CREDITO;
import static br.com.vrbeneficios.domain.enums.TipoMovimentoEnum.DEBITO;
import static java.math.BigDecimal.ZERO;

import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import br.com.vrbeneficios.domain.enums.TipoMovimentoEnum;
import br.com.vrbeneficios.repository.MovimentoCartaoRepository;
import br.com.vrbeneficios.service.MovimentoCartaoService;
import java.math.BigDecimal;
import java.util.List;
import java.util.function.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * The type Movimento cartao service.
 */
@Service
@RequiredArgsConstructor
public class MovimentoCartaoServiceImpl implements MovimentoCartaoService {
    private final MovimentoCartaoRepository movimentoCartaoRepository;

    @Override
    public MovimentoCartao efetuarMovimentoCartao(MovimentoCartao movimentoCartao) {
        return movimentoCartaoRepository.saveAndFlush(movimentoCartao);
    }

    @Override
    public BigDecimal calcularSaldoCartao(String numeroCartao) {
        List<MovimentoCartao> movimentoCartaosByCartao =
                movimentoCartaoRepository.findMovimentoCartaosByNumeroCartao(numeroCartao);
        BigDecimal credito = movimentoCartaosByCartao.stream()
                .filter(getMovimentoCartaoCreditoPredicate(CREDITO))
                .map(MovimentoCartao::getValor)
                .reduce(ZERO, BigDecimal::add);
        BigDecimal debito = movimentoCartaosByCartao.stream()
                .filter(getMovimentoCartaoCreditoPredicate(DEBITO))
                .map(MovimentoCartao::getValor)
                .reduce(ZERO, BigDecimal::add);
        return credito.subtract(debito);
    }

    private Predicate<MovimentoCartao> getMovimentoCartaoCreditoPredicate(TipoMovimentoEnum credito) {
        return movimentoCartao -> credito.equals(movimentoCartao.getTipoMovimento());
    }
}
