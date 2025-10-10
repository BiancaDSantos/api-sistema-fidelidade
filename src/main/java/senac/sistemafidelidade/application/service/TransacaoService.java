package senac.sistemafidelidade.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.domain.model.Transacao;
import senac.sistemafidelidade.domain.model.enums.TipoTransacao;
import senac.sistemafidelidade.domain.repository.ContaFidelidadeRepository;
import senac.sistemafidelidade.domain.repository.TransacaoRepository;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.TransacaoDTO;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaFidelidadeRepository contaFidelidadeRepository;
    private final ContaFidelidadeService contaFidelidadeService;

    @Transactional
    public TransacaoDTO.TransacaoResponse processarTransaca(
            Long empresaId, TransacaoDTO.TransacaoRequest transacaoRequest) {

        /*
        *
        *verifico se a empresa existe
        *bloqueia transação no banco
        *
        *
        *
        * */
        
        // Guarda o saldo anterior
        Long saldoAnterior = conta.getPontos().longValue();

        // Calcula o novo saldo usando o multiplicador do enum
        Long novoSaldo = calcularNovoSaldo(saldoAnterior, request);

        // Atualiza o saldo na conta
        conta.setPontos(novoSaldo.intValue());
        contaFidelidadeRepository.save(conta);

        // Cria o registro da transação
        Transacao transacao = Transacao.builder()
                .contaFidelidade(conta)
                .pontos(request.pontos())
                .tipoTransacao(request.tipoTransacao())
                .descricao(request.descricao())
                .dataOperacao(LocalDateTime.now())
                .build();

        transacao = transacaoRepository.save(transacao);


        //separar lógica para o DTO
        return new TransacaoDTO.TransacaoResponse(
                transacao.getId(),
                conta.getCliente().getId(),
                conta.getCliente().getNome(),
                conta.getEmpresa().getId(),
                conta.getEmpresa().getNome(),
                request.pontos(),
                request.tipoTransacao(),
                request.descricao(),
                saldoAnterior,
                novoSaldo,
                transacao.getDataOperacao()
        );


    }

    private Integer calcularPontos(Integer pontos, TipoTransacao tipoTransacao){

        validarSaldo(pontos, tipoTransacao);

        return pontos * tipoTransacao.getMultiplicador();
    }

    private void validarSaldo (Integer pontos, TipoTransacao tipoTransacao, Integer saldo){
        if (tipoTransacao.getMultiplicador() < 1 && pontos < saldo) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a operação");
        }
    }

    private ContaFidelidade obterOuCriarContaComLockPessimista(Long clienteId, Long empresaId){
        return contaFidelidadeRepository
                .findByClienteIdAndEmpresaIdForUpdate(clienteId, empresaId)
                .orElseGet(() -> contaFidelidadeService
                        .criarContaFidelidade(clienteId, empresaId));
    }


}