package dbp.proyecto.playlistUser;

import jakarta.persistence.Column;

import java.util.Objects;

public class PlaylistUserId {
    @Column(name = "playlist_id")
    private Long playlistId;

    @Column(name = "user_id")
    private Long userId;

    public PlaylistUserId() {}

    public PlaylistUserId(Long playlistId, Long userId) {
        this.playlistId = playlistId;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaylistUserId that = (PlaylistUserId) o;

        if (!Objects.equals(playlistId, that.playlistId)) return false;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        int result = playlistId != null ? playlistId.hashCode() : 0;
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        return result;
    }
}
