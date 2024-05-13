package dbp.proyecto.playlist;

import dbp.proyecto.playlistSongs.PlaylistSongs;
import dbp.proyecto.playlistUser.PlaylistUser;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Playlist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist() {
    }

    @OneToMany(mappedBy = "playlist",
            orphanRemoval = true
    )
    private List<PlaylistUser> authors = new ArrayList<>();

    @OneToMany(mappedBy = "playlist",
            orphanRemoval = true
    )
    private List<PlaylistSongs> songs = new ArrayList<>();


}
