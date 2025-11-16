package senac.sistemafidelidade.infrastructure.adapters.secondary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.domain.ports.ContaFidelidadeRepositoryPort;
import senac.sistemafidelidade.domain.repository.ContaFidelidadeRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ContaFidelidaedRepositoryAdapter implements ContaFidelidadeRepositoryPort {

    private final ContaFidelidadeRepository repository;


    @Override
    public ContaFidelidade save(ContaFidelidade conta) {
        return null;
    }

    @Override
    public Optional<ContaFidelidade> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<ContaFidelidade> findAll() {
        return List.of();
    }

    @Override
    public List<ContaFidelidade> findByEmpresaId(Long empresaId) {
        return List.of();
    }

    @Override
    public Optional<ContaFidelidade> findByIdAndEmpresaId(Long contaId, Long empresaId) {
        return Optional.empty();
    }

    @Override
    public Optional<ContaFidelidade> findByClienteIdAndEmpresaIdForUpdate(Long clienteId, Long empresaId) {
        return Optional.empty();
    }

    @Override
    public void deleteById(Long id) {

    }
}
