package senac.sistemafidelidade.infrastructure.adapters.primary.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import senac.sistemafidelidade.domain.model.Empresa;

import java.util.List;

public interface EmpresaDTO {

    record CriarEmpresaRequest(

            @NotBlank(message = "O nome deve ser preenchido")
            @Size(min = 1, max = 100)
            String razaoSocial,

            @NotBlank(message = "O CNPJ deve ser preenchido")
            @Size(min = 1, max = 18)
            String cnpj
    )
    {
        public Empresa transformaEmEntitade() {
            Empresa empresa = new Empresa();
            empresa.setRazaoSocial(this.razaoSocial);
            empresa.setCnpj(this.cnpj);
            return empresa;
        }
    }

    record EmpresaResponse(
            Long id,
            String razaoSocial,
            String cnpj
    ){
        public EmpresaResponse(Empresa empresa) {
            this(
                    empresa.getId(),
                    empresa.getRazaoSocial(),
                    empresa.getCnpj()
            );
        }
    }

    public static List<EmpresaResponse> fromEntityList(List<Empresa> empresas) {
        return empresas.stream().map(EmpresaResponse::new).toList();
    }
}
