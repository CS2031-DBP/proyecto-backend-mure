package dbp.proyecto.content;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class Content {
    @ManyToOne
    private Song song;
    @ManyToOne
    private Playlist playlist;
    private Timestamp date;
    private Integer likes;
}
