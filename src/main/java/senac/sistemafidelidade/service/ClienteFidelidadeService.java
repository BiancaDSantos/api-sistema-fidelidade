package senac.sistemafidelidade.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.dto.ClienteFidelidadeDTO;
import senac.sistemafidelidade.model.ClienteFidelidade;
import senac.sistemafidelidade.repository.ClienteFidelidadeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteFidelidadeService {

    private final ClienteFidelidadeRepository clienteRepository;


    public ClienteFidelidade criarCliente(ClienteFidelidadeDTO.CriarClienteRequest request) {

        if (clienteRepository.existsByEmail(request.email())) throw new RuntimeException("Login já existente");

        ClienteFidelidade cliente = request.transformaEmModel();

        return clienteRepository.save(cliente);
    }

    public List<ClienteFidelidade> listarClientes() {
        return clienteRepository.findAll();
    }

    public ClienteFidelidade buscarClientePorId(Long id) {
        return clienteRepository.findById(id).orElseThrow(() -> new RuntimeException("Id não encontrado"));
    }

    @Transactional
    public ClienteFidelidade adicionarPontos(ClienteFidelidadeDTO.OperacaoPontosRequest request) {
        ClienteFidelidade cliente = clienteRepository.findAndLockById(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));

        cliente.setPontos(cliente.getPontos() + request.pontos());
        return clienteRepository.save(cliente);
    }

    public ClienteFidelidade resgatarPontos(ClienteFidelidadeDTO.@Valid OperacaoPontosRequest request) {
        ClienteFidelidade cliente = clienteRepository.findAndLockById(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Id não encontrado"));

        if(cliente.getPontos() < request.pontos()) throw new RuntimeException("Gastando mais que tem");

        cliente.setPontos(cliente.getPontos() - request.pontos());
        return clienteRepository.save(cliente);
    }
}
