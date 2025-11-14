package senac.sistemafidelidade.application.service;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.domain.model.Cliente;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.domain.model.Empresa;
import senac.sistemafidelidade.domain.repository.ClienteRepository;
import senac.sistemafidelidade.domain.repository.ContaFidelidadeRepository;
import senac.sistemafidelidade.domain.repository.EmpresaRepository;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.ClienteFidelidadeDTO;

import java.util.List;

@Service
@AllArgsConstructor
public class ContaFidelidadeService {
    
    private final ContaFidelidadeRepository contaFidelidadeRepository;
    private final ClienteRepository clienteRepository;
    private final EmpresaRepository empresaRepository;
    
    public ContaFidelidade criarCliente(ClienteFidelidadeDTO.@Valid CriarClienteRequest request) {

        ContaFidelidade conta = request.transformaEmModel();
        return contaFidelidadeRepository.save(conta);
    }

    public List<ContaFidelidade> listarClientes() {
        return contaFidelidadeRepository.findAll();
    }

    public ContaFidelidade buscarClientePorId(Long id) {
        return contaFidelidadeRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Cliente não encontrado"));
    }

    public ContaFidelidade criarContaFidelidade(Long clienteId, Long empresaId) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado para o id: " + clienteId));

        Empresa empresa = empresaRepository.findById(empresaId)
                .orElseThrow(() -> new IllegalArgumentException("Empresa não encontrada para o id: " + empresaId));

        ContaFidelidade novaConta = ContaFidelidade.builder()
                .cliente(cliente)
                .empresa(empresa)
                .pontos(0)
                .build();

        return contaFidelidadeRepository.save(novaConta);
    }
}
