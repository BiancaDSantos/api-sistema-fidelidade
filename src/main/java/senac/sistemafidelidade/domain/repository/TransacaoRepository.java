package senac.sistemafidelidade.domain.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.domain.model.Transacao;

import java.util.Optional;

public interface TransacaoRepository extends
        JpaRepository<Transacao, Long>
{
    Optional<ContaFidelidade> findByClieenteIdAndEmpresaId(Long clienteId, Long empresaId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM ContaFidelidade c WHERE c.cliente.id = :clienteId AND c.empresa.id = :empresaId")
    Optional<ContaFidelidade> findByClienteIdAndEmpresaIdForUpdate(
            @Param("clienteId") Long clienteId,
            @Param("empresaId") Long empresaId
    );
}
