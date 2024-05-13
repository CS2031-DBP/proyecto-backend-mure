package dbp.proyecto.playlistUser;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.user.User;
import jakarta.persistence.*;

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

    public PlaylistUserId getId() {
        return id;
    }

    public void setId(PlaylistUserId id) {
        this.id = id;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
