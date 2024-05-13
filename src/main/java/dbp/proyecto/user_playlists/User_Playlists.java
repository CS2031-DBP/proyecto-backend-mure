package dbp.proyecto.user_playlists;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Getter
@Entity
public class User_Playlists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long user_id;
    private Long playlist_id;

    public User_Playlists() {}

    public User_Playlists(Long id, Long user_id, Long playlist_id) {
        this.id = id;
        this.user_id = user_id;
        this.playlist_id = playlist_id;
    }
}
