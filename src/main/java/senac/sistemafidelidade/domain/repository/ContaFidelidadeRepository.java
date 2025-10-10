package senac.sistemafidelidade.domain.repository;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import senac.sistemafidelidade.domain.model.ContaFidelidade;

import java.util.Optional;

public interface ContaFidelidadeRepository extends
        JpaRepository<ContaFidelidade, Long>
{

}
