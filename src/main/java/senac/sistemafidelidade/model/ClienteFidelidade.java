package senac.sistemafidelidade.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Entity(name = "clientefidelidade")
@NoArgsConstructor
@Builder
public class ClienteFidelidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome deve ser preenchido")
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String nome;

    @Email
    @Size(min = 9, max = 100)
    @NotBlank(message = "O e-mail deve ser preenchido")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotNull(message = "Os pontos não podem ser nulo")
    @PositiveOrZero(message = "Apenas valores positivo ou zero")
    private Integer pontos;

}
