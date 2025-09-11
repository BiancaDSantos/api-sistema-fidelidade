package senac.sistemafidelidade.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.dto.LoginRequestDto;
import senac.sistemafidelidade.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public boolean validarSenha(LoginRequestDto login) {
        return usuarioRepository.existsUsuarioByEmailContainingAndSenha(login.email(), login.senha());
    }
}