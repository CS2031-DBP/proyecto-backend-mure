package dbp.proyecto.authentication.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SigninDto {
    private String email;
    private String nickname;
    private String password;
    private String name;
    private String lastname;
    private LocalDate birthdate;
    private Boolean isAdmin = false;
}
