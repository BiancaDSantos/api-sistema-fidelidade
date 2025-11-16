package senac.sistemafidelidade.infrastructure.adapters.primary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import senac.sistemafidelidade.domain.enums.UserRoles;
import senac.sistemafidelidade.domain.model.Usuario;

public interface UsuarioDTO {

    record CriarUsuarioRequest(

            @NotBlank(message = "O nome é obrigatório")
            @Size(min = 2, max = 100, message = "Nome deve ter entre 2 e 100 caracteres")
            String nome,

            @Email(message = "O e-mail deve ser válido")
            @NotBlank(message = "O e-mail é obrigatório")
            String email,

            @NotBlank(message = "A senha é obrigatória")
            @Size(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
            String senha,

            // Role opcional - se não informado, será USER por padrão
            UserRoles role
    ) {
        public Usuario toEntity() {
            Usuario usuario = new Usuario();
            usuario.setNome(this.nome);
            usuario.setEmail(this.email);
            usuario.setSenha(this.senha); // Será criptografada no service
            usuario.setRole(this.role);
            return usuario;
        }
    }

    record AtualizarSenhaRequest(

            @NotBlank(message = "A nova senha é obrigatória")
            @Size(min = 6, max = 50, message = "A senha deve ter entre 6 e 50 caracteres")
            String novaSenha
    ) {}

    record UsuarioResponse(
            Integer id,
            String nome,
            String email,
            UserRoles role
            // Senha nunca é retornada por segurança
    ) {
        public UsuarioResponse(Usuario usuario) {
            this(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getEmail(),
                    usuario.getRole()
            );
        }
    }
}
