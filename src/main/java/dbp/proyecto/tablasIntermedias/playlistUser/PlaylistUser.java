package dbp.proyecto.tablasIntermedias.playlistUser;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class PlaylistUser {
    @EmbeddedId
    private PlaylistUserId id;

    public PlaylistUser(Playlist playlist, User user) {
        this.playlist = playlist;
        this.user = user;
        this.id = new PlaylistUserId(playlist.getId(), user.getId());
    }

    public PlaylistUser() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
}
