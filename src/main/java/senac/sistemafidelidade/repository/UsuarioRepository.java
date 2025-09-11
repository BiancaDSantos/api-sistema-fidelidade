package senac.sistemafidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senac.sistemafidelidade.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailAndNome(String email, String nome);

    boolean existsUsuarioByEmailContainingAndSenha(String email, String senha);
}