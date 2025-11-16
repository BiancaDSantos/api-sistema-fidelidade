package senac.sistemafidelidade.infrastructure.adapters.primary.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import senac.sistemafidelidade.domain.exeception.ContaNaoEncontradaException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    //Inserir tratamento de exceções do @Valid

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        Map<String, Object> erro = criarErroResponse(
                "CREDENCIAIS_INVALIDAS",
                ex.getMessage(),
                HttpStatus.UNAUTHORIZED.value()
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
    }

    @ExceptionHandler(ContaNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleContaNaoEncontradaException(ContaNaoEncontradaException ex) {
        Map<String, Object> erro = criarErroResponse(
                "CONTA_NAO_ENCONTRADA",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex) {
        Map<String, Object> erro = criarErroResponse(
                "ARGUMENTO_INVALIDO",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> erro = criarErroResponse(
                "DADOS_INVALIDOS",
                "Dados de entrada inválidos",
                HttpStatus.BAD_REQUEST.value()
        );

        // Adiciona detalhes dos campos inválidos
        Map<String, String> camposInvalidos = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                camposInvalidos.put(error.getField(), error.getDefaultMessage())
        );
        erro.put("camposInvalidos", camposInvalidos);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
    }

    private Map<String, Object> criarErroResponse(String codigo, String mensagem, int status) {
        Map<String, Object> erro = new HashMap<>();
        erro.put("timestamp", LocalDateTime.now());
        erro.put("status", status);
        erro.put("codigo", codigo);
        erro.put("mensagem", mensagem);
        return erro;
    }
}
