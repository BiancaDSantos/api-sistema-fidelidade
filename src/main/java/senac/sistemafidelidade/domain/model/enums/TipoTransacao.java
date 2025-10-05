package senac.sistemafidelidade.domain.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Tipos de transação disponívies no sistema")
public enum TipoTransacao {

    @Schema(description = "Acúmulo de pontos")
    ACUMULO,

    @Schema(description = "Resgate de pontos")
    RESGATE
}
