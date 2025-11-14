package senac.sistemafidelidade.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import senac.sistemafidelidade.domain.model.Transacao;

public interface TransacaoRepository extends
        JpaRepository<Transacao, Long>
{

}
