package dbp.proyecto.user.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequestDto {
    private MultipartFile profileImage;
    private String name;
    private String password;
    private String email;
}
