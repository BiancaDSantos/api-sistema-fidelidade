package senac.sistemafidelidade.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity(name = "cliente")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cliente {

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

}