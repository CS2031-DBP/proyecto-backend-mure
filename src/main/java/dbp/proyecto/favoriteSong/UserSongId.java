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
}
