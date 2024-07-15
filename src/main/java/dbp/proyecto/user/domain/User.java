package dbp.proyecto.user.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.comment.domain.Comment;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.story.domain.Story;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Role role;

    @NotBlank
    private String name;

    @NotBlank
    @Column(unique = true)
    private String nickname;

    @NotBlank
    private String nicknameNormalized;

    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Size(min = 8, max = 64)
    private String password;

    @NotNull
    private LocalDate birthDate;

    private LocalDateTime createdAt;

    private String profileImageUrl = "https://www.pngall.com/wp-content/uploads/5/User-Profile-PNG-Free-Download.png";

    private String expoPushToken;

    @ManyToMany
    private List<User> friends;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Post> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Playlist> ownsPlaylists;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "user_albums", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "album_id"))
    private List<Album> favoriteAlbums;

    @ManyToMany
    @JoinTable(name = "user_song", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "song_id"))
    private List<Song> favoriteSongs;

    @ManyToMany(mappedBy = "likedBy", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Post> likedPosts;

    @Transient
    private String rolePrefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolePrefix + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
