package senac.sistemafidelidade.application.service;

import org.springframework.stereotype.Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginRequestDto;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginResponseDTO;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioService usuarioService;
    private final TokenService tokenService;

    @Operation(description = "Busca e valida o login.")
    public LoginResponseDTO authenticate(LoginRequestDto request) {

        if(!usuarioService.validarSenha(request)) {
            throw new BadCredentialsException("Credenciais inválidas.");
        }

        var usuario = usuarioService.getUsuarioByLogin(request);
        String token = tokenService.gerarToken(usuario);

        return new LoginResponseDTO(token);
    }

}
