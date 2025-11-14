package senac.sistemafidelidade.infrastructure.adapters.primary.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import senac.sistemafidelidade.application.service.AuthService;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginRequestDto;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginResponseDTO;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Endpoint de autenticação dos usuários e geração de token.")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Realiza o login do usuário",
            description = "Autentica com email e senha e retorna um token|JWT em caso de sucesso."
    )
    public ResponseEntity<LoginResponseDTO> login(
            @Valid @RequestBody LoginRequestDto request
    ) {
        var loginResponse = authService.authenticate(request);
        return ResponseEntity.ok(loginResponse);
    }
}