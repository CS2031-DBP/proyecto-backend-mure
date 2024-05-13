package dbp.proyecto.playlist;

import dbp.proyecto.PlaylistSongs.PlaylistSongs;
import dbp.proyecto.user.User;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany
    private List<User> authors;

    @ManyToMany
    private List<PlaylistSongs> playlistSongs;

    public Playlist() {}

    public Playlist(Long id, String name, List<User> authors, List<PlaylistSongs> songs) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        this.playlistSongs = songs;
    }
}
