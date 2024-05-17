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

    private String name;


    public Playlist(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Playlist() {
    }

    @OneToMany(mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PlaylistUser> authors = new ArrayList<>();

    @OneToMany(mappedBy = "playlist",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PlaylistSongs> songs = new ArrayList<>();


}