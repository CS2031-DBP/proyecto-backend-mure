package dbp.proyecto.favoriteSong;

import jakarta.persistence.Column;

import java.util.Objects;

public class UserSongId {
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "song_id")
    private Long songId;

    public UserSongId() {
    }

    public UserSongId(Long userId, Long songId) {
        this.userId = userId;
        this.songId = songId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserSongId that = (UserSongId) o;

        if (!Objects.equals(userId, that.userId)) return false;
        return Objects.equals(songId, that.songId);
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (songId != null ? songId.hashCode() : 0);
        return result;
    }
}
