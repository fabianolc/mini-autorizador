package br.com.vrbeneficios.repository;

import br.com.vrbeneficios.domain.entity.MovimentoCartao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Movimento cartao repository.
 */
@Repository
public interface MovimentoCartaoRepository extends JpaRepository<MovimentoCartao, Long> {
    /**
     * Find movimento cartaos by numero cartao list.
     *
     * @param numeroCartao the numero cartao
     * @return the list
     */
    List<MovimentoCartao> findMovimentoCartaosByNumeroCartao(String numeroCartao);
}
