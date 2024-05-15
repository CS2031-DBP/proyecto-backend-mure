package dbp.proyecto.content;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.OneToMany;

import javax.xml.stream.events.Comment;
import java.sql.Timestamp;
import java.util.List;


public class Content {
    private Song song;
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
