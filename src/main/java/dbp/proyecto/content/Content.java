package dbp.proyecto.content;

import java.sql.Timestamp;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
public class Content {

    @ManyToOne
    private Song song;

    @ManyToOne
    private Playlist playlist;

    private Timestamp date;

    private Integer likes;

    public Content(Song song, Playlist playlist) {
        this.song = song;
        this.playlist = playlist;
        this.date = new Timestamp(System.currentTimeMillis());
        this.likes = 0;
    }
}
