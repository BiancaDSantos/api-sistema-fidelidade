package senac.sistemafidelidade.domain.ports;

import jakarta.validation.Valid;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.ClienteFidelidadeDTO;

import java.util.List;

public interface ContaFidelidadeServicePort {

    ContaFidelidade criarCliente(ClienteFidelidadeDTO.@Valid CriarClienteRequest request);

    List<ContaFidelidade> listarClientes();

    List<ContaFidelidade> listarClientesPorEmpresa(Long empresaId);

    ContaFidelidade buscarClientePorId(Long id);

    ContaFidelidade buscarClientePorIdEEmpresa(Long id, Long empresaId);

    ContaFidelidade criarContaFidelidade(Long clienteId, Long empresaId);
}
