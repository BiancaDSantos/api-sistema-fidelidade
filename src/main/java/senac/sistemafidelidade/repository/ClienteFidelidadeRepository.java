package senac.sistemafidelidade.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNullApi;
import senac.sistemafidelidade.model.ClienteFidelidade;

import java.util.Optional;

public interface ClienteFidelidadeRepository extends JpaRepository<ClienteFidelidade, Long> {

    Optional<ClienteFidelidade> findByEmail(String email);
    boolean existsByEmail(String email);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM clientefidelidade c WHERE c.id = :id")
    Optional<ClienteFidelidade> findAndLockById(@Param("id") Long id);

}
