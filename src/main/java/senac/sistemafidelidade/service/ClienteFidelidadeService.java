package senac.sistemafidelidade.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.dto.ClienteFidelidadeDTO;
import java.util.List;
import senac.sistemafidelidade.dto.ClienteFidelidadeDTO.*;
import senac.sistemafidelidade.model.ClienteFidelidade;
import senac.sistemafidelidade.repository.ClienteFidelidadeRepository;

@Service
@RequiredArgsConstructor
public class ClienteFidelidadeService {

    private final ClienteFidelidadeRepository clienteRepository;

    @Transactional
    public ClienteFidelidadeDTO.ClienteResponse criarCliente(ClienteFidelidadeDTO.CriarClienteRequest request) {

        if (clienteRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Já existe um cliente com o email: " + request.email());
        }

        ClienteFidelidade cliente = new ClienteFidelidade();
        cliente.setNome(request.nome());
        cliente.setEmail(request.email());
        cliente.setPontos(request.pontos());

        cliente = clienteRepository.save(cliente);
        return toResponse(cliente);
    }

    public List<ClienteFidelidadeDTO.ClienteResponse> listarClientes() {
        return clienteRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    public ClienteFidelidadeDTO.ClienteResponse buscarClientePorId(Long id) {
        ClienteFidelidade cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        return toResponse(cliente);
    }

    @Transactional
    public ClienteFidelidadeDTO.ClienteResponse adicionarPontos(ClienteFidelidadeDTO.OperacaoPontosRequest request) {
        if (request.pontos() <= 0) {
            throw new RuntimeException("A quantidade de pontos deve ser maior que zero");
        }

        int linhasAfetadas = clienteRepository.adicionarPontosAtomicamente(
                request.clienteId(),
                request.pontos()
        );

        if (linhasAfetadas == 0) {
            throw new RuntimeException("Cliente não encontrado com ID: " + request.clienteId());
        }

        ClienteFidelidade cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado após operação"));

        return toResponse(cliente);
    }

    @Transactional
    public ClienteFidelidadeDTO.ClienteResponse resgatarPontos(ClienteFidelidadeDTO.OperacaoPontosRequest request) {
        if (request.pontos() <= 0) {
            throw new RuntimeException("A quantidade de pontos deve ser maior que zero");
        }

        int linhasAfetadas = clienteRepository.resgatarPontosSeSaldoSuficiente(
                request.clienteId(),
                request.pontos()
        );

        if (linhasAfetadas == 0) {
            Integer saldoAtual = clienteRepository.findPontosByClienteId(request.clienteId())
                    .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

            throw new RuntimeException(
                    "Saldo de pontos insuficiente. Saldo atual: " + saldoAtual +
                            ", Tentativa de resgate: " + request.pontos()
            );
        }

        ClienteFidelidade cliente = clienteRepository.findById(request.clienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado após operação"));

        return toResponse(cliente);
    }

    public ClienteFidelidadeDTO.SaldoResponse consultarSaldo(Long clienteId) {
        ClienteFidelidade cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + clienteId));

        return new ClienteFidelidadeDTO.SaldoResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getPontos()
        );
    }

    private ClienteFidelidadeDTO.ClienteResponse toResponse(ClienteFidelidade cliente) {
        return new ClienteFidelidadeDTO.ClienteResponse(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getPontos()
        );
    }
}
