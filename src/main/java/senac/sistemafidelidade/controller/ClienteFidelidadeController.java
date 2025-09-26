package senac.sistemafidelidade.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.dto.ClienteFidelidadeDTO;
import senac.sistemafidelidade.service.ClienteFidelidadeService;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteFidelidadeController {

    private final ClienteFidelidadeService clienteService;

    @PostMapping
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> criarCliente(
            @Valid @RequestBody ClienteFidelidadeDTO.CriarClienteRequest request
    ) {
        return ResponseEntity.ok(new ClienteFidelidadeDTO.ClienteResponse(clienteService.criarCliente(request)));
    }

    @GetMapping
    public ResponseEntity<List<ClienteFidelidadeDTO.ClienteResponse>> listarClientes() {

        List<ClienteFidelidadeDTO.ClienteResponse> clientes =
                ClienteFidelidadeDTO.ClienteResponse
                        .geraListaDeResponse(clienteService.listarClientes());

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> buscarCliente(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(new ClienteFidelidadeDTO.ClienteResponse(clienteService.buscarClientePorId(id)));
    }

    @PostMapping("/adicionar-pontos")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> adicionarPontos(
            @Valid @RequestBody ClienteFidelidadeDTO.OperacaoPontosRequest request
    ) {
        return ResponseEntity.ok(new ClienteFidelidadeDTO.ClienteResponse(clienteService.adicionarPontos(request)));
    }

    @PostMapping("/resgatar-pontos")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> resgatarPontos(
            @Valid @RequestBody ClienteFidelidadeDTO.OperacaoPontosRequest request
    ) {
        return ResponseEntity.ok(new ClienteFidelidadeDTO.ClienteResponse(clienteService.resgatarPontos(request)));
    }

}
