package senac.sistemafidelidade.infrastructure.adapters.primary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.application.service.UsuarioService;
import senac.sistemafidelidade.domain.model.Usuario;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.UsuarioDTO;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
@Tag(
        name = "Usuário",
        description = "Endpoints para o gerenciamento de usuários do sistema."
)
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário por ID")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> consultaPorId(@PathVariable Integer id) {
        return usuarioService.buscarPorId(id)
                .map(usuario -> ResponseEntity.ok(new UsuarioDTO.UsuarioResponse(usuario)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    @PreAuthorize("hasRole('ADMIN')") // Só admin pode listar usuários
    public ResponseEntity<List<UsuarioDTO.UsuarioResponse>> consultaTodos() {
        List<UsuarioDTO.UsuarioResponse> usuarios = usuarioService.listarUsuarios()
                .stream()
                .map(UsuarioDTO.UsuarioResponse::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuarios);
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuário")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> criarUsuario(
            @Valid @RequestBody UsuarioDTO.CriarUsuarioRequest request) {
        try {
            Usuario usuario = request.toEntity();
            Usuario usuarioSalvo = usuarioService.criarUsuario(usuario);

            return ResponseEntity.ok(new UsuarioDTO.UsuarioResponse(usuarioSalvo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/admin")
    @Operation(summary = "Cadastra um usuário administrador")
    @PreAuthorize("hasRole('ADMIN')") // Só admin pode criar admin
    public ResponseEntity<UsuarioDTO.UsuarioResponse> criarUsuarioAdmin(
            @Valid @RequestBody UsuarioDTO.CriarUsuarioRequest request) {
        try {
            Usuario usuario = request.toEntity();
            Usuario usuarioSalvo = usuarioService.criarUsuarioAdmin(usuario);

            return ResponseEntity.ok(new UsuarioDTO.UsuarioResponse(usuarioSalvo));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}/senha")
    @Operation(summary = "Atualiza a senha do usuário")
    public ResponseEntity<UsuarioDTO.UsuarioResponse> atualizarSenha(
            @PathVariable Integer id,
            @Valid @RequestBody UsuarioDTO.AtualizarSenhaRequest request) {
        try {
            Usuario usuarioAtualizado = usuarioService.atualizarSenha(id, request.novaSenha());
            return ResponseEntity.ok(new UsuarioDTO.UsuarioResponse(usuarioAtualizado));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/check-email/{email}")
    @Operation(summary = "Verifica se email já existe")
    public ResponseEntity<Boolean> verificarEmail(@PathVariable String email) {
        boolean existe = usuarioService.emailJaExiste(email);
        return ResponseEntity.ok(existe);
    }
}