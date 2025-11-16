package senac.sistemafidelidade.application.service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.domain.enums.UserRoles;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.LoginRequestDto;
import senac.sistemafidelidade.domain.model.Usuario;
import senac.sistemafidelidade.domain.repository.UsuarioRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Operation(description = "Valida as credenciais do usuário comparando a senha fornecida com a hash armazenada")
    public boolean validarSenha(LoginRequestDto login) {
        log.debug("🔐 Validando credenciais para email: {}", login.email());

        Optional<Usuario> usuario = usuarioRepository.findByEmail(login.email());

        if (usuario.isEmpty()) {
            log.warn("❌ Usuário não encontrado: {}", login.email());
            return false;
        }

        boolean senhaValida = passwordEncoder.matches(login.senha(), usuario.get().getSenha());

        if (senhaValida) {
            log.info("✅ Credenciais válidas para: {}", login.email());
        } else {
            log.warn("❌ Senha inválida para: {}", login.email());
        }

        return senhaValida;
    }

    @Operation(description = "Busca usuário pelo email para geração do token")
    public Usuario getUsuarioByLogin(LoginRequestDto login) {
        return usuarioRepository.findByEmail(login.email()).orElse(null);
    }

    @Operation(description = "ria um novo usuário com senha criptografada e role padrão")
    public Usuario criarUsuario(Usuario usuario) {
        log.info("👤 Criando novo usuário: {}", usuario.getEmail());

        // Verifica se email já existe
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("E-mail já está em uso: " + usuario.getEmail());
        }

        // Define role padrão se não especificada
        if (usuario.getRole() == null) {
            usuario.setRole(UserRoles.USER);
            log.debug("🔧 Role USER definida por padrão para: {}", usuario.getEmail());
        }

        // Criptografa a senha
        String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCriptografada);

        log.debug("🔒 Senha criptografada para: {}", usuario.getEmail());

        Usuario usuarioSalvo = usuarioRepository.save(usuario);
        log.info("✅ Usuário criado com sucesso - ID: {}, Email: {}",
                usuarioSalvo.getId(), usuarioSalvo.getEmail());

        return usuarioSalvo;
    }

    @Operation(description = "Cria um usuário administrador")
    public Usuario criarUsuarioAdmin(Usuario usuario) {
        log.info("👑 Criando usuário administrador: {}", usuario.getEmail());

        usuario.setRole(UserRoles.ADMIN);
        return criarUsuario(usuario);
    }

    @Operation(description = "Busca usuário por ID")
    public Optional<Usuario> buscarPorId(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Operation(summary = "Busca todos os usuários",
            description = "Lista todos os usuários (sem expor senhas)"
    )
    public List<Usuario> listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();

        usuarios.forEach(usuario -> usuario.setSenha("***"));

        return usuarios;
    }

    @Operation(description = "Verifica se o e-mail já existe")
    public boolean emailJaExiste(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    @Operation(description = "Atualiza senha do usuário")
    public Usuario atualizarSenha(Integer usuarioId, String novaSenha) {
        log.info("🔄 Atualizando senha do usuário ID: {}", usuarioId);

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        String senhaCriptografada = passwordEncoder.encode(novaSenha);
        usuario.setSenha(senhaCriptografada);

        Usuario usuarioAtualizado = usuarioRepository.save(usuario);
        log.info("✅ Senha atualizada para usuário ID: {}", usuarioId);

        return usuarioAtualizado;
    }
}