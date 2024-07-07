package dbp.proyecto.authentication.dto;

import lombok.Data;

@Data
public class UserPasswordVerificationRequestDto {
    private Long userId;
    private String password;
}
