package senac.sistemafidelidade.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import senac.sistemafidelidade.model.Token;
import senac.sistemafidelidade.model.Usuario;
import senac.sistemafidelidade.repository.TokenRepository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${api.security.token.secret}")
    private String secret;

    @Autowired
    private TokenRepository tokenRepository;

    private final String EMISSOR = "ControlaStock API";

    public String gerarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer(EMISSOR)
                    .withSubject(usuario.getUsername())
                    .withExpiresAt(gerarDataExpiracao())
                    .sign(algorithm);

            tokenRepository.save(new Token(null, token, usuario));

            return token;
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validarToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer(EMISSOR)
                    .build()
                    .verify(token)
                    .getSubject();
        } catch (JWTVerificationException exception){
            return "";
        }
    }

    private Instant gerarDataExpiracao() {
        
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}