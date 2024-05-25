package dbp.proyecto.user.dto;

import dbp.proyecto.user.domain.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBasicInfoResponseDTO {
    private String name;
    private LocalDate birthDate;
    private String email;
    private String profileImage;
    private Role role;
}
