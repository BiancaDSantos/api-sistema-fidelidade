package senac.sistemafidelidade.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import senac.sistemafidelidade.model.Token;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> { // O tipo do ID foi alterado para Integer.

    Optional<Token> findByToken(String token);
}
