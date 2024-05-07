package dbp.proyecto.user;

import jakarta.persistence.*;
import lombok.Data;

import javax.xml.stream.events.Comment;
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

    private String profileImage;

    @ManyToMany
    private List<User> friends;

    //añadir tabla intermedia para las playlists todo

    public User(Long id, String name, String email, String password, String profileImage, List<User> friends) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.profileImage = profileImage;
        this.friends = friends;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
