package senac.sistemafidelidade.application.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.domain.enums.TipoTransacao;
import senac.sistemafidelidade.domain.model.ContaFidelidade;
import senac.sistemafidelidade.domain.model.Transacao;
import senac.sistemafidelidade.domain.repository.ContaFidelidadeRepository;
import senac.sistemafidelidade.domain.repository.TransacaoRepository;
import senac.sistemafidelidade.infrastructure.adapters.primary.dto.TransacaoDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class TransacaoService {

    private final TransacaoRepository transacaoRepository;
    private final ContaFidelidadeRepository contaFidelidadeRepository;
    private final ContaFidelidadeService contaFidelidadeService;

    @Transactional
    public TransacaoDTO.TransacaoResponse processarTransacao(Long empresaId, TransacaoDTO.TransacaoRequest transacaoRequest) {

        ContaFidelidade conta = obterOuCriarContaComLockPessimista(transacaoRequest.clienteId(), empresaId);

        long saldoAnterior = conta.getPontos();
        long novoSaldo = calcularNovoSaldo(saldoAnterior, transacaoRequest.pontos(), transacaoRequest.tipoTransacao());

        conta.setPontos((int) novoSaldo);
        contaFidelidadeRepository.save(conta);

        Transacao transacao = criarRegistroTransacao(conta, transacaoRequest);
        transacaoRepository.save(transacao);

        return criarTransacaoResponse(transacao, conta, saldoAnterior);
    }

    private long calcularNovoSaldo(long saldoAtual, long pontos, TipoTransacao tipoTransacao) {
        validarOperacao(saldoAtual, pontos, tipoTransacao);

        long variacao = pontos * tipoTransacao.getMultiplicador();
        return saldoAtual + variacao;
    }

    private void validarOperacao(long saldoAtual, long pontos, TipoTransacao tipoTransacao) {
        if (tipoTransacao.getMultiplicador() < 0 && pontos > saldoAtual) {
            throw new IllegalArgumentException("Saldo insuficiente para realizar a operação de resgate.");
        }
    }

    private ContaFidelidade obterOuCriarContaComLockPessimista(Long clienteId, Long empresaId) {
        return contaFidelidadeRepository
                .findByClienteIdAndEmpresaIdForUpdate(clienteId, empresaId)
                .orElseGet(() -> contaFidelidadeService.criarContaFidelidade(clienteId, empresaId));
    }

    private Transacao criarRegistroTransacao(ContaFidelidade conta, TransacaoDTO.TransacaoRequest request) {
        return Transacao.builder()
                .contaFidelidade(conta)
                .pontos(request.pontos())
                .tipoTransacao(request.tipoTransacao())
                .descricao(request.descricao())
                .data(Instant.now())
                .build();
    }

    private TransacaoDTO.TransacaoResponse criarTransacaoResponse(Transacao transacao, ContaFidelidade conta, long saldoAnterior) {
        return new TransacaoDTO.TransacaoResponse(
                transacao.getId(),
                conta.getCliente().getId(),
                conta.getCliente().getNome(),
                conta.getEmpresa().getId(),
                conta.getEmpresa().getRazaoSocial(),
                transacao.getPontos(),
                transacao.getTipoTransacao(),
                transacao.getDescricao(),
                saldoAnterior,
                conta.getPontos().longValue(),
                LocalDateTime.ofInstant(transacao.getData(), ZoneOffset.of("-03:00"))
        );
    }
}