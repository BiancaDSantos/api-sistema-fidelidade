package senac.sistemafidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senac.sistemafidelidade.model.Usuario;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByEmailAndNome(String email, String nome);
    boolean existsByEmail(String email);
    boolean existsUsuarioByEmailContainingAndSenha(String email, String senha);

    Optional<Usuario> findByEmail(String email);
}