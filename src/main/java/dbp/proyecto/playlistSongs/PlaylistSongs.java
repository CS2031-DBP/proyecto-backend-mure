package dbp.proyecto.playlistSongs;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PlaylistSongs{
    @EmbeddedId
    private PlaylistSongId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;

    public PlaylistSongs() {}

    public PlaylistSongs(Playlist playlist, Song song) {
        this.id = new PlaylistSongId(playlist.getId(), song.getId());
        this.playlist = playlist;
        this.song = song;
    }
}
