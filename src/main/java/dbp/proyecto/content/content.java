package dbp.proyecto.content;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.OneToMany;

import javax.xml.stream.events.Comment;
import java.sql.Timestamp;
import java.util.List;


public class content {
    private Song song;
    private Playlist playlist;
    private Timestamp date;
    private Integer likes;
    private String author;

    @OneToMany
    private List<Comment> coments;
}
