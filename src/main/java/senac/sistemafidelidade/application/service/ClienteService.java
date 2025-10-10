package senac.sistemafidelidade.application.service;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.domain.model.Cliente;
import senac.sistemafidelidade.domain.repository.ClienteRepository;

@Schema(description = "Lógica do cliente")
@Service
@AllArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public Cliente cadastrarCliente(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

}
