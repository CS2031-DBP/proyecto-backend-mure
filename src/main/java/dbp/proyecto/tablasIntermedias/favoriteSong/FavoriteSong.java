package dbp.proyecto.tablasIntermedias.favoriteSong;

import dbp.proyecto.song.Song;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;

@Entity
public class FavoriteSong {
    @EmbeddedId
    private UserSongId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    @JoinColumn(name = "song_id")
    private Song song;

    public FavoriteSong() {}

    public FavoriteSong(User user, Song song) {
        this.user = user;
        this.song = song;
        this.id = new UserSongId(user.getId(), song.getId());
    }
}
