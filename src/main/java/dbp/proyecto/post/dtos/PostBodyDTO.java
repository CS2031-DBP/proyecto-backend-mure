package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.ManyToOne;
import lombok.Getter;

@Getter
public class PostBodyDTO {
    private Song song;

    private Playlist playlist;

    private String description;

    private String imageurl;

    private String audioUrl;
}

