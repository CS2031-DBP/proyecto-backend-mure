package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;

public class PostResponseDTO {

    private Song song;

    private Playlist playlist;

    private String description;

    private String imageurl;

    private String audioUrl;

    public PostResponseDTO(String description, String imageurl, String audioUrl) {
        this.description = description;
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

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
}
