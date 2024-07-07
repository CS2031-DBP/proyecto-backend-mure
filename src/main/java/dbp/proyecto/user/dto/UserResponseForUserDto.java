package dbp.proyecto.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserResponseForUserDto {
    private Long id;
    private String name;
    private String profileImageUrl;
    private List<String> friendsNames;
    private List<Long> friendsIds;
}
