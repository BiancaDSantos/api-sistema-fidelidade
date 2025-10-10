package senac.sistemafidelidade.infrastructure.adapters.primary.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import senac.sistemafidelidade.domain.model.Cliente;
import senac.sistemafidelidade.domain.model.ContaFidelidade;

public interface ClienteDTO {

    record CriarClienteRequest(

            @NotBlank
            @Size(min = 5, max = 250, message = "O campo nome deve conter entre 5 e 250")
            String nome,

            @NotBlank
            @Size(min = 5, max = 250, message = "O campo e-mail deve conter entre 5 e 250")
            @Email String email
    ) {
        public Cliente toEntity() {
            Cliente cliente = new Cliente();
            cliente.setNome(this.nome);
            cliente.setEmail(this.email);
            return cliente;
        }
    }

    record AtualizarClienteRequest(
            @NotBlank String nome,
            @Email String email
    ) {}

}
