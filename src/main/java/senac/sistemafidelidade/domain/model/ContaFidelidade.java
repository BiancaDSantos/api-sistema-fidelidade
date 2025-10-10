package senac.sistemafidelidade.domain.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(
        name = "conta_fidelidade", uniqueConstraints = {
            @UniqueConstraint(
                    columnNames = {
                        "cliente_id",
                        "empresa_id"
                    }
            )
        }
)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Modelo de conta fidelidade")
public class ContaFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "empresa_id", nullable = false)
    private Empresa empresa;

    @NotNull(message = "A pontuação não pode ser nula")
    @PositiveOrZero(message = "Apenas valores positivo ou zero")
    private Integer pontos;

    @Size(min = 1, max = 200)
    private String descricao;

}
