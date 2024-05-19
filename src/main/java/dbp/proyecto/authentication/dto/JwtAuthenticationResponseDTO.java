package dbp.proyecto.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtAuthenticationResponseDTO {
    @NotBlank
    private final String accessToken;
    private String tokenType = "Bearer";

    public JwtAuthenticationResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }
}
