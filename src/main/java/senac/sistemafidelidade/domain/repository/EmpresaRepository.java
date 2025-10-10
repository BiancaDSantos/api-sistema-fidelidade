package senac.sistemafidelidade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senac.sistemafidelidade.domain.model.Empresa;

public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
}
