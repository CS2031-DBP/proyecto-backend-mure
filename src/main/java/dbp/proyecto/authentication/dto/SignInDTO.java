package dbp.proyecto.authentication.dto;

import lombok.Data;

@Data
public class SignInDTO {
    private String email;
    private String password;
    private String name;
    private Integer age;
    private Boolean isAdmin = false;
}
