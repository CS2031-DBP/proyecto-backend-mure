package dbp.proyecto.user.dto;

import dbp.proyecto.user.domain.Role;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserBasicInfoResponseDTO {
    private Long id;
    private String name;
    private LocalDate birthDate;
    private String email;
    private String profileImage;
    private Role role;
}
