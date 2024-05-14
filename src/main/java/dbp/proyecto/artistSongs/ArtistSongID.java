package dbp.proyecto.artistSongs;

import jakarta.persistence.Column;

public class ArtistSongID {

    @Column(name = "artist_id")
    private Long artistId;

    @Column(name = "song_id")
    private Long songId;

    public ArtistSongID() {
    }

    public ArtistSongID(Long artistId, Long songId) {
        this.artistId = artistId;
        this.songId = songId;
    }


}
