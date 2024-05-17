package dbp.proyecto.favoriteSong;

import dbp.proyecto.song.Song;
import jakarta.persistence.*;

@Entity
public class FavoriteSong {
    @EmbeddedId
    private UserSongId id;

    public FavoriteSong() {
    }

    public FavoriteSong(User User, Song song) {
        this.User = User;
        this.song = song;
        this.id = new UserSongId(User.getId(), song.getId());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User User;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;
}
