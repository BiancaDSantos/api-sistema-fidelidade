package senac.sistemafidelidade.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Tipos de transação disponívies no sistema")
public enum TipoTransacao {

    @Schema(description = "Acúmulo de pontos")
    ACUMULO(1),

    @Schema(description = "Resgate de pontos")
    RESGATE(-1),

    @Schema(description = "Expiração de pontos")
    EXPIRACAO(-1),

    @Schema(description = "Ajuste de pontos positivo")
    AJUSTE_POSITIVO(1),

    @Schema(description = "Ajuste de pontos negativo")
    AJUSTE_NEGATIVO(-1),

    ;

    private final int multiplicador;

    TipoTransacao(final int multiplicador) {
        this.multiplicador = multiplicador;
    }

}
