package dbp.proyecto.content;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
<<<<<<< HEAD
=======
import jakarta.persistence.ManyToOne;
>>>>>>> d47e6b1a1961c79f7ea5f7079d5d558b67dc507a
import java.sql.Timestamp;

public class Content {
    @ManyToOne
    private Song song;
    @ManyToOne
    private Playlist playlist;
    private Timestamp date;
    private Integer likes;

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public Playlist getPlaylist() {
        return playlist;
    }

    public void setPlaylist(Playlist playlist) {
        this.playlist = playlist;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
