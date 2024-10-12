package dbp.proyecto.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UserRequestDto {
    @Size(min = 3, max = 20)
    private MultipartFile profileImage;
    private String name;
    private String password;
    private String email;
    private String nickname;
}
