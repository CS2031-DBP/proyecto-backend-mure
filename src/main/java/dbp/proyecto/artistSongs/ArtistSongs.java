package dbp.proyecto.artistSongs;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.Song;
import jakarta.persistence.*;


@Entity
public class ArtistSongs {
    @EmbeddedId
    private ArtistSongId id;

    public ArtistSongs(Artist artist, Song song) {
        this.artist = artist;
        this.song = song;
        this.id = new ArtistSongId(artist.getId(), song.getId());
    }

    public ArtistSongs() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("artistId")
    private Artist artist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;
}
