package dbp.proyecto.playlist.domain;

<<<<<<< HEAD
import dbp.proyecto.playlistSongs.PlaylistSongs;
import dbp.proyecto.playlistUser.PlaylistUser;
import dbp.proyecto.song.domain.Song;
=======
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;
>>>>>>> 49cee68097f9fd7afb72f8a6054d40ed7c672e6f
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

    public Playlist(Long id, String name) {
        this.id = id;
        this.name = name;
        this.songs = new ArrayList<>();
    }

    public Playlist() {}

    public void addSong(PlaylistSongs playlistSong) {
        this.songs.add(playlistSong);
    }
}
