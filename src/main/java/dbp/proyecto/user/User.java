package dbp.proyecto.user.domain;

import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;

import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    private String profileImage;

    @ManyToMany
    private List<User> friends;

    @OneToMany(mappedBy = "User")
    private List<Post> posts;

    @OneToMany(mappedBy = "User")
    private List<Story> stories;

    @OneToMany(mappedBy = "User")
    private List<FavoriteSong> favoriteSongs;

    @OneToMany(mappedBy = "User")
    private List<PlaylistUser> playlists;

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }
    public User() {
    }
}
