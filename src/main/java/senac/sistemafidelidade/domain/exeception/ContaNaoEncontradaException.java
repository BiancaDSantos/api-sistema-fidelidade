package senac.sistemafidelidade.domain.exeception;

public class ContaNaoEncontradaException extends RuntimeException{

    public ContaNaoEncontradaException(String message) {
        super(message);
    }

    public ContaNaoEncontradaException(Long contaId, Long empresaId) {
        super(String.format("Conta com ID %d não encontrada para a empresa %d", contaId, empresaId));
    }
}
