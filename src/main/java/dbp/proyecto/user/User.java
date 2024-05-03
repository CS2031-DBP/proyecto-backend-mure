package dbp.proyecto.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String profileName;

    @ManyToMany
    private List<User> friends;
}
