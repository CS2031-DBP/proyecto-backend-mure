package dbp.proyecto.playlistUser;

import dbp.proyecto.playlist.Playlist;
import jakarta.persistence.*;

@Entity
public class PlaylistUser {
    @EmbeddedId
    private PlaylistUserId id;

    public PlaylistUser(Playlist playlist, User User) {
        this.playlist = playlist;
        this.User = User;
        this.id = new PlaylistUserId(playlist.getId(), User.getId());
    }

    public PlaylistUser() {
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User User;

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
        return User;
    }

    public void setUser(User User) {
        this.User = User;
    }
}
