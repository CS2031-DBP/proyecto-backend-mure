package dbp.proyecto.content;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import java.sql.Timestamp;


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
