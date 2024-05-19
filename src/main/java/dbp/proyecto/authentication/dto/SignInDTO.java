package dbp.proyecto.authentication.dto;

import dbp.proyecto.user.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String name;
    @NotNull
    private Role role;
}
