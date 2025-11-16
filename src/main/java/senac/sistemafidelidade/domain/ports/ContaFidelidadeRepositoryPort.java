package senac.sistemafidelidade.domain.ports;

import senac.sistemafidelidade.domain.model.ContaFidelidade;

import java.util.List;
import java.util.Optional;

public interface ContaFidelidadeRepositoryPort {

    ContaFidelidade save(ContaFidelidade conta);

    Optional<ContaFidelidade> findById(Long id);

    List<ContaFidelidade> findAll();

    List<ContaFidelidade> findByEmpresaId(Long empresaId);

    Optional<ContaFidelidade> findByIdAndEmpresaId(Long contaId, Long empresaId);

    Optional<ContaFidelidade> findByClienteIdAndEmpresaIdForUpdate(Long clienteId, Long empresaId);

    void deleteById(Long id);
}
