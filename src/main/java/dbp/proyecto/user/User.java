package dbp.proyecto.user;

import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.favoriteSong.FavoriteSong;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.story.domain.Story;
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

    @OneToMany(mappedBy = "user")
    private List<Post> Posts;

    @OneToMany(mappedBy = "user")
    private List<Story> stories;

    @OneToMany(mappedBy = "user")
    private List<FavoriteSong> FavoriteSongs;

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

    public List<Post> getPosts() {
        return Posts;
    }

    public void setPosts(List<Post> Posts) {
        this.Posts = Posts;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    public List<FavoriteSong> getFavoriteSongs() {
        return FavoriteSongs;
    }

    public void setFavoriteSongs(List<FavoriteSong> FavoriteSongs) {
        this.FavoriteSongs = FavoriteSongs;
    }
}
