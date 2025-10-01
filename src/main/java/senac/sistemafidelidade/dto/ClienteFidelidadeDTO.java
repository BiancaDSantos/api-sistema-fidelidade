package senac.sistemafidelidade.dto;


import jakarta.validation.constraints.*;
import senac.sistemafidelidade.model.ClienteFidelidade;

import java.util.List;

public interface ClienteFidelidadeDTO {

    record CriarClienteRequest(

            @NotBlank(message = "Nome é obrigatório")
            String nome,

            @Email(message = "Email deve ser válido")
            @NotBlank(message = "Email é obrigatório")
            String email,

            @NotNull(message = "Pontos não podem ser nulos")
            @PositiveOrZero(message = "Pontos devem ser maior ou igual a zero")
            Integer pontos

    ) {
        public ClienteFidelidade transformaEmModel() {
            return ClienteFidelidade.builder()
                    .nome(this.nome())
                    .email(this.email())
                    .pontos(this.pontos())
                    .build();
        }
    }

    record ClienteResponse(

            Long id,
            String nome,
            String email,
            Integer pontos

    ) {
        public ClienteResponse(ClienteFidelidade clienteFidelidade) {
            this(
                    clienteFidelidade.getId(),
                    clienteFidelidade.getNome(),
                    clienteFidelidade.getEmail(),
                    clienteFidelidade.getPontos()
            );
        }

        public static List<ClienteResponse> geraListaDeResponse(List<ClienteFidelidade> clientes) {
            return clientes.stream()
                    .map(ClienteResponse::new)
                    .toList();
        }
    }

    record OperacaoPontosRequest(

            @NotNull(message = "Quantidade de pontos é obrigatória")
            @Positive(message = "Pontos devem ser maiores que zero")
            Integer pontos

    ) {}

}
