package dbp.proyecto.artistSongs;

import jakarta.persistence.Column;

public class ArtistSongId {

    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "song_id")
    private Long songId;

    public ArtistSongId() {
    }

    public ArtistSongId(Long artistId, Long songId) {
        this.artistId = artistId;
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ArtistSongId that = (ArtistSongId) o;

        if (!artistId.equals(that.artistId)) return false;
        return songId.equals(that.songId);
    }

    @Override
    public int hashCode() {
        int result = artistId.hashCode();
        result = 31 * result + songId.hashCode();
        return result;
    }
}
