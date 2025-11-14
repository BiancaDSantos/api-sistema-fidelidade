package senac.sistemafidelidade.infrastructure.adapters.primary.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.application.service.ContaFidelidadeService;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.ClienteFidelidadeDTO;

import java.util.List;

@RestController
@RequestMapping("/conta_fidelidade_cliente")
@RequiredArgsConstructor
public class ContaFidelidadeController {

    private final ContaFidelidadeService contaFidelidadeService;

    @PostMapping
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> criarCliente(
            @Valid @RequestBody ClienteFidelidadeDTO.CriarClienteRequest request
    ) {
        return ResponseEntity.ok(
                new ClienteFidelidadeDTO.ClienteResponse(
                        contaFidelidadeService.criarCliente(request))
        );
    }

    @GetMapping
    public ResponseEntity<List<ClienteFidelidadeDTO.ClienteResponse>> listarClientes() {

        List<ClienteFidelidadeDTO.ClienteResponse> clientes =
                ClienteFidelidadeDTO.ClienteResponse
                        .geraListaDeResponse(contaFidelidadeService.listarClientes());

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> buscarCliente(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ClienteFidelidadeDTO.ClienteResponse(
                        contaFidelidadeService.buscarClientePorId(id))
        );
    }
}
