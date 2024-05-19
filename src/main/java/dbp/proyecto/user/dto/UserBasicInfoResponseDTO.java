package dbp.proyecto.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserBasicInfoResponseDTO {
    private String name;
    private Integer age;
    private String email;
    private String profileImage;
}
