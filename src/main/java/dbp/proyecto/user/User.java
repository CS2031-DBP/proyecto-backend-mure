package dbp.proyecto.user;

import dbp.proyecto.PlaylistUser.PlaylistUser;
import dbp.proyecto.favoriteSong.favoriteSong;
import dbp.proyecto.post.post;
import dbp.proyecto.story.story;
import jakarta.persistence.*;
import lombok.Data;

import javax.xml.stream.events.Comment;
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

    @OneToMany(mappedBy = "user")
    private List<post> posts;

    @OneToMany(mappedBy = "user")
    private List<story> stories;

    @OneToMany(mappedBy = "user")
    private List<favoriteSong> favoriteSongs;

    @OneToMany(mappedBy = "user")
    private List<PlaylistUser> playlists;

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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

    public List<post> getPosts() {
        return posts;
    }

    public void setPosts(List<post> posts) {
        this.posts = posts;
    }

    public List<story> getStories() {
        return stories;
    }

    public void setStories(List<story> stories) {
        this.stories = stories;
    }

    public List<favoriteSong> getFavoriteSongs() {
        return favoriteSongs;
    }

    public void setFavoriteSongs(List<favoriteSong> favoriteSongs) {
        this.favoriteSongs = favoriteSongs;
    }
}
