package dbp.proyecto.user.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.story.domain.Story;
import jakarta.persistence.*;
import lombok.Data;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

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
    @Size(min = 3, max = 50)
    @Column(unique = true)
    private String name;

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

    private String profileImage = "https://www.pngall.com/wp-content/uploads/5/User-Profile-PNG-Free-Download.png";

    @ManyToMany
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL , orphanRemoval = true)
    private Set<Post> posts = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Story> stories = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Playlist> ownsPlaylists = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_albums",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    private Set<Album> favoriteAlbums = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "user_song",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "song_id"))
    private Set<Song> favoriteSongs = new HashSet<>();

    @Transient
    private String rolePrefix = "ROLE_";

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(new SimpleGrantedAuthority(rolePrefix + role.name()));
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
