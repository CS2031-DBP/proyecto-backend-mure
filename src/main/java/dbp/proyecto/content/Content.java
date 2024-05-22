package dbp.proyecto.content;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.ManyToOne;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.sql.Timestamp;

@Data
public class Content {
    @ManyToOne
    private Song song;
    @ManyToOne
    private Album album;
    @NotNull
    private Timestamp date;
    @NotNull
    private Integer likes;
}
