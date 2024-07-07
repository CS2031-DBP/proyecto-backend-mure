package dbp.proyecto.user.dto;

import dbp.proyecto.user.domain.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class UserResponseDto {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String profileImageUrl;
    private Role role;
    private List<Long> friendsIds;
    private String username;
}
