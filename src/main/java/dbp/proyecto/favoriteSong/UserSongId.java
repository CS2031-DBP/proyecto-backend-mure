package dbp.proyecto.favoriteSong;

import jakarta.persistence.Column;

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

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return songId != null ? songId.equals(that.songId) : that.songId == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (songId != null ? songId.hashCode() : 0);
        return result;
    }
}
