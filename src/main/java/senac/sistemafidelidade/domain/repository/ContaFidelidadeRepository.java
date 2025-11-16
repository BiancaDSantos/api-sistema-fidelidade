package senac.sistemafidelidade.domain.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senac.sistemafidelidade.domain.model.ContaFidelidade;

import java.lang.ScopedValue;
import java.util.List;
import java.util.Optional;

public interface ContaFidelidadeRepository extends
        JpaRepository<ContaFidelidade, Long>
{
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM ContaFidelidade c WHERE c.cliente.id = :clienteId AND c.empresa.id = :empresaId")
    Optional<ContaFidelidade> findByClienteIdAndEmpresaIdForUpdate(
            @Param("clienteId") Long clienteId,
            @Param("empresaId") Long empresaId
    );

    List<ContaFidelidade> findByEmpresaId(Long empresaId);

    @Query("SELECT c FROM ContaFidelidade c WHERE c.id = :contaId AND c.empresa.id = :empresaId")
    Optional<ContaFidelidade> findByIdAndEmpresaId(
            @Param("contaId") Long contaId,
            @Param("empresaId") Long empresaId
    );
}
