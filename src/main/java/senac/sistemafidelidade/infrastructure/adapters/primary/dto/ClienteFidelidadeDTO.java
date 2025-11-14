
package senac.sistemafidelidade.infrastructure.adapters.primary.dto;

import jakarta.validation.constraints.*;
import senac.sistemafidelidade.domain.model.Cliente;
import senac.sistemafidelidade.domain.model.ContaFidelidade;

import java.util.List;

public interface ClienteFidelidadeDTO {

record CriarClienteRequest(


        @NotBlank(message = "Nome é obrigatório")
        String nome,

        @Email(message = "Email deve ser válido")
        @NotBlank(message = "Email é obrigatório")
        String email,

        @NotNull(message = "Os pontos não podem ser nulos")
        @PositiveOrZero(message = "Os pontos devem ser maior ou igual a zero")
        Integer pontos

) {
    public ContaFidelidade transformaEmModel() {

        // monta o Cliente
        Cliente cliente = Cliente.builder()
                .nome(this.nome())
                .email(this.email())
                .build();

        // monta a ContaFidelidade associando o cliente
        return ContaFidelidade.builder()
                .cliente(cliente)
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
    public ClienteResponse(ContaFidelidade clienteFidelidade) {
        this(
                clienteFidelidade.getId(),
                clienteFidelidade.getCliente().getNome(),
                clienteFidelidade.getCliente().getEmail(),
                clienteFidelidade.getPontos()
        );
    }

    public static List<ClienteResponse> geraListaDeResponse(List<ContaFidelidade> clientes) {
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