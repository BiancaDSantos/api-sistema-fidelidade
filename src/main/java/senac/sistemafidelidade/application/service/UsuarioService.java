package senac.sistemafidelidade.application.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginRequestDto;
import senac.sistemafidelidade.domain.model.Usuario;
import senac.sistemafidelidade.domain.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequestDto login) {
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());
    }

    public Usuario getUsuarioByLogin(LoginRequestDto login) {
        return usuarioRepository.findByEmail(login.email()).orElse(null);
    }
}