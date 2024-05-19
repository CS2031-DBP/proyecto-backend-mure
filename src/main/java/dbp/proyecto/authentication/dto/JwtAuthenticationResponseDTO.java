package dbp.proyecto.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtAuthenticationResponseDTO {
    @NotBlank
    private String token;
}
