package dbp.proyecto.user.dto;

import lombok.Data;

@Data
public class UserBodyDTO {
    private String profileImage;
    private String name;
    private String password;
    private String email;
}
