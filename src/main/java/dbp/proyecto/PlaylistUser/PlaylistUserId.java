package dbp.proyecto.PlaylistUser;

import jakarta.persistence.Column;

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
}
