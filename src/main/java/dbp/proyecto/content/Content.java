package dbp.proyecto.content;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
<<<<<<< HEAD
=======
import jakarta.persistence.ManyToOne;
<<<<<<< HEAD
>>>>>>> d47e6b1a1961c79f7ea5f7079d5d558b67dc507a
=======
import lombok.Getter;
import lombok.Setter;

>>>>>>> 49cee68097f9fd7afb72f8a6054d40ed7c672e6f
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
