package senac.sistemafidelidade.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.dto.ClienteFidelidadeDTO;
import senac.sistemafidelidade.service.ClienteFidelidadeService;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteFidelidadeController {

    private final ClienteFidelidadeService clienteService;

    @PostMapping
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> criarCliente(
            @Valid @RequestBody ClienteFidelidadeDTO.CriarClienteRequest request) {
        ClienteFidelidadeDTO.ClienteResponse response = clienteService.criarCliente(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ClienteFidelidadeDTO.ClienteResponse>> listarClientes() {
        List<ClienteFidelidadeDTO.ClienteResponse> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> buscarCliente(@PathVariable Long id) {
        ClienteFidelidadeDTO.ClienteResponse cliente = clienteService.buscarClientePorId(id);
        return ResponseEntity.ok(cliente);
    }

    @PostMapping("/adicionar-pontos")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> adicionarPontos(
            @Valid @RequestBody ClienteFidelidadeDTO.OperacaoPontosRequest request) {
        ClienteFidelidadeDTO.ClienteResponse response = clienteService.adicionarPontos(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/resgatar-pontos")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> resgatarPontos(
            @Valid @RequestBody ClienteFidelidadeDTO.OperacaoPontosRequest request) {
        ClienteFidelidadeDTO.ClienteResponse response = clienteService.resgatarPontos(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<ClienteFidelidadeDTO.SaldoResponse> consultarSaldo(@PathVariable Long id) {
        ClienteFidelidadeDTO.SaldoResponse saldo = clienteService.consultarSaldo(id);
        return ResponseEntity.ok(saldo);
    }
}
