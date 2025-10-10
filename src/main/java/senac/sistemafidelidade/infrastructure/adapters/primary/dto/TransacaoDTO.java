package senac.sistemafidelidade.infrastructure.adapters.primary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import senac.sistemafidelidade.domain.model.enums.TipoTransacao;

import java.time.LocalDateTime;

public interface TransacaoDTO {

    record TransacaoRequest(

            @NotNull(message = "Valor da transação é obrigatório")
            @Positive(message = "Valor da transação deve ser positivo")
            Long pontos,

            @NotNull(message = "Tipo de transação é obrigatório")
            TipoTransacao tipoTransacao,

            @Size(max = 500, message = "Descrição não pode ter mais de 500 caracteres")
            String descricao
    ) {}

    record TransacaoResponse(

            Long id,
            Long clienteId,
            String nomeCliente,
            Long empresaId,
            String nomeEmpresa,
            Long pontos,
            TipoTransacao tipoTransacao,
            String descricao,
            Long saldoAnterior,
            Long saldoAtual,
            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
            LocalDateTime dataOperacao

    ) {}

}
