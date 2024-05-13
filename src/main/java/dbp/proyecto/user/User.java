package dbp.proyecto.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;

    private String password;

    private String profileImage;

    @ManyToMany
    private List<User> friends;

    // TODO: Añadir tabla intermedia para las playlists

    public User() {}

    public User(Long id, String name, String email, String password, String profileImage, List<User> friends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.friends = friends;
    }
}
