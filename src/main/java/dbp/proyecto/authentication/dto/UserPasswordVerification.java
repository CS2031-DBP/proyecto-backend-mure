package dbp.proyecto.authentication.dto;

import lombok.Data;

@Data
public class UserPasswordVerification {
    private Long userId;
    private String password;
}
