package dbp.proyecto.user.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoForUserDTO {
    private Long id;
    private String name;
    private List<String> friendsNames;
}
