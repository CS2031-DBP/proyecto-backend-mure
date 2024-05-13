package dbp.proyecto.playlist;

import dbp.proyecto.playlistSongs.PlaylistSongs;
import dbp.proyecto.playlistUser.PlaylistUser;
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

    @OneToMany(mappedBy = "playlist")
    private List<PlaylistUser> authors;

    @OneToMany(mappedBy = "playlist")
    private List<PlaylistSongs> PlaylistSongs;

    public Playlist(Long id, String name, List<PlaylistUser> authors, List<dbp.proyecto.playlistSongs.PlaylistSongs> playlistSongs) {
        this.id = id;
        this.name = name;
        this.authors = authors;
        PlaylistSongs = playlistSongs;
    }

    public Playlist() {
    }


}
