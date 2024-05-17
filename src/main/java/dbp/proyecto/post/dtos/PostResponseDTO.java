package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
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

}
