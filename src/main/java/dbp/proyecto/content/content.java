package dbp.proyecto.content;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;

import javax.xml.stream.events.Comment;
import java.sql.Timestamp;
import java.util.List;


public class content {
    private Song song;
    private Playlist playlist;
    private Timestamp date;
    private Integer likes;
    private String author;
    private List<Comment> coments;
}
