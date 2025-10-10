package senac.sistemafidelidade.infrastructure.adapters.primary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginRequestDto;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginResponseDTO;
import senac.sistemafidelidade.application.service.TokenService;
import senac.sistemafidelidade.application.service.UsuarioService;

@RestController
@RequestMapping("/auth")
@Tag(
        name = "Autenticação",
        description = "Endpoint de autenticação dos usuários e geração de token."
)
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    @Operation(
            summary = "Realiza o login do usuário",
            description = "Autentica com email e senha e retorna um token|JWT em caso de sucesso."
    )
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        try {
            if(!usuarioService.validarSenha(request)){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            var usuario = usuarioService.getUsuarioByLogin(request);
            String token = tokenService.gerarToken(usuario);

            var loginResponse = new LoginResponseDTO(token);

            return ResponseEntity.ok(loginResponse);

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}