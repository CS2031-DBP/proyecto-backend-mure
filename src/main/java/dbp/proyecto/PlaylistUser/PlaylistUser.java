package dbp.proyecto.PlaylistUser;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.Data;

@Data
public class PlaylistUser {
    @EmbeddedId
    private PlaylistUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;

    public PlaylistUser() {}

    public PlaylistUser(Playlist playlist, User user) {
        this.id = new PlaylistUserId(playlist.getId(), user.getId());
        this.playlist = playlist;
        this.user = user;
    }
}
