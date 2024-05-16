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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaylistSongId that = (PlaylistSongId) o;

        if (playlistId != null ? !playlistId.equals(that.playlistId) : that.playlistId != null) return false;
        return songId != null ? songId.equals(that.songId) : that.songId == null;
    }

    @Override
    public int hashCode() {
        int result = playlistId != null ? playlistId.hashCode() : 0;
        result = 31 * result + (songId != null ? songId.hashCode() : 0);
        return result;
    }
}
