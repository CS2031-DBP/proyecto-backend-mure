package dbp.proyecto.PlaylistSongs;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.*;

@Entity

public class PlaylistSongs{
    @EmbeddedId
    private PlaylistSongId id;

    public PlaylistSongs(Playlist playlist, Song song) {
        this.playlist = playlist;
        this.song = song;
        this.id = new PlaylistSongId(playlist.getId(), song.getId());
    }

    public PlaylistSongs() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;

    public PlaylistSongId getId() {
        return id;
    }

    public void setId(PlaylistSongId id) {
        this.id = id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }
}
