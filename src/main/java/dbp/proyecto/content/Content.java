package dbp.proyecto.content;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.ManyToOne;


import java.sql.Timestamp;

public class Content {

    @ManyToOne
    private Song song;

    @ManyToOne
    private Playlist playlist;

    private Timestamp date;
    private Integer likes = 0;

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
