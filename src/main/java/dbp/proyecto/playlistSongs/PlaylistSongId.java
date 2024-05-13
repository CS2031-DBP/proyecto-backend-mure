package dbp.proyecto.playlistSongs;

import jakarta.persistence.Column;

public class PlaylistSongId {
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "song_id")
    private Long songId;

    public PlaylistSongId() {}

    public PlaylistSongId(Long playlistId, Long songId) {
        this.playlistId = playlistId;
        this.songId = songId;
    }
}
