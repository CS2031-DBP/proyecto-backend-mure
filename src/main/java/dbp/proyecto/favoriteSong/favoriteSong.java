package dbp.proyecto.favoriteSong;

import dbp.proyecto.song.Song;
import dbp.proyecto.user.User;
import jakarta.persistence.*;

@Entity
public class favoriteSong {
    @EmbeddedId
    private UserSongId id;

    public favoriteSong() {
    }

    public favoriteSong(User user, Song song) {
        this.user = user;
        this.song = song;
        this.id = new UserSongId(user.getId(), song.getId());
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("songId")
    private Song song;
}
