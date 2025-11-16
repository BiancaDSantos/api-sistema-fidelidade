package senac.sistemafidelidade.infrastructure.adapters.primary.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.domain.ports.ContaFidelidadeServicePort;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.ClienteFidelidadeDTO;

import java.util.List;

@RestController
@RequestMapping("/conta")
@RequiredArgsConstructor
public class ContaFidelidadeController {

    private final ContaFidelidadeServicePort contaFidelidadeService;

    @GetMapping("/empresa/{empresaId}")
    @Operation(summary = "Lista contas por empresa")
    public ResponseEntity<List<ClienteFidelidadeDTO.ClienteResponse>> listarContasPorEmpresa(
            @PathVariable Long empresaId
    ) {
        List<ClienteFidelidadeDTO.ClienteResponse> contas =
                ClienteFidelidadeDTO.ClienteResponse.geraListaDeResponse(
                        contaFidelidadeService.listarClientesPorEmpresa(empresaId)
                );

        return ResponseEntity.ok(contas);
    }

    @GetMapping("/{contaId}/empresa/{empresaId}")
    @Operation(summary = "Busca conta por ID e empresa")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> buscarContaPorIdEEmpresa(
            @PathVariable Long contaId,
            @PathVariable Long empresaId
    ) {
        return ResponseEntity.ok(
                new ClienteFidelidadeDTO.ClienteResponse(
                        contaFidelidadeService.buscarClientePorIdEEmpresa(contaId, empresaId)
                )
        );
    }

    @PostMapping
    @Operation(summary = "Criar nova conta de fidelidade")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> criarCliente(
            @Valid @RequestBody ClienteFidelidadeDTO.CriarClienteRequest request
    ) {
        return ResponseEntity.ok(
                new ClienteFidelidadeDTO.ClienteResponse(
                        contaFidelidadeService.criarCliente(request)
                )
        );
    }

    @GetMapping("/listar")
    @Operation(summary = "Listar todas as contas")
    public ResponseEntity<List<ClienteFidelidadeDTO.ClienteResponse>> listarClientes() {
        List<ClienteFidelidadeDTO.ClienteResponse> clientes =
                ClienteFidelidadeDTO.ClienteResponse
                        .geraListaDeResponse(contaFidelidadeService.listarClientes());

        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar conta por ID")
    public ResponseEntity<ClienteFidelidadeDTO.ClienteResponse> buscarCliente(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                new ClienteFidelidadeDTO.ClienteResponse(
                        contaFidelidadeService.buscarClientePorId(id)
                )
        );
    }
}
