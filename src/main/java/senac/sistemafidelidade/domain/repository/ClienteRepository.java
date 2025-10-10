package senac.sistemafidelidade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senac.sistemafidelidade.domain.model.Cliente;

public interface ClienteRepository extends
        JpaRepository<Cliente, Long>
{

}
