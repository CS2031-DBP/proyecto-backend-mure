package dbp.proyecto.playlist.domain;

import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
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

    @Column(unique = true)
    private String name;

    public Playlist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist() {
    }

    @OneToMany(mappedBy = "playlist")
    private List<PlaylistUser> users = new ArrayList<>();

    @OneToMany(mappedBy = "playlist"    )
    private List<PlaylistSongs> songs = new ArrayList<>();

}
