package br.com.vrbeneficios.repository;

import br.com.vrbeneficios.domain.entity.Cartao;
import jakarta.persistence.LockModeType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

/**
 * The interface Cartao repository.
 */
@Repository
public interface CartaoRepository extends JpaRepository<Cartao, String> {

    @Override
    @Lock(LockModeType.PESSIMISTIC_READ)
    Optional<Cartao> findById(String numeroCartao);
}
