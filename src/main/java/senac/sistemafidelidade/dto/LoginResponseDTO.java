package senac.sistemafidelidade.dto;

public record LoginResponseDTO(
        String token,
        String tipo,
        Long expiresIn
) {
    public LoginResponseDTO(String token) {
        this(token, "Bearer", 3600L);
    }
}