package dbp.proyecto.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;

}
