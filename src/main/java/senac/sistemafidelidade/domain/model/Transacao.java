package senac.sistemafidelidade.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import senac.sistemafidelidade.domain.enums.TipoTransacao;

import java.time.Instant;

@Entity(name = "transacao")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Transacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoTransacao tipoTransacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contaFidelidade", nullable = false)
    private ContaFidelidade contaFidelidade;

    @Column(nullable = false)
    private Long pontos;

    @Column(nullable = false)
    private String descricao;

    @Column(nullable = false)
    @Builder.Default
    private Instant data = Instant.now();

}
