package senac.sistemafidelidade.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity(name = "empresa")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome deve ser preenchido")
    @Size(min = 1, max = 100)
    @Column(nullable = false, length = 100)
    private String razaoSocial;

    @NotBlank(message = "O CNPJ deve ser preenchido")
    @Size(min = 1, max = 18)
    @Column(nullable = false, length = 18)
    private String cnpj;

}
