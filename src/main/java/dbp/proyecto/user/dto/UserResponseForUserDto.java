package dbp.proyecto.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseForUserDto {
    private Long id;
    private String name;
    private String profileImage;
    private List<String> friendsNames;
    private List<Long> friendsIds;
}
