package dbp.proyecto.tablasIntermedias.playlistUser;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class PlaylistUser {
    @EmbeddedId
    private PlaylistUserId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("playlistId")
    private Playlist playlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    private User user;
}
