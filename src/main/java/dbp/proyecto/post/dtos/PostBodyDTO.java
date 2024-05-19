package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;

import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostBodyDTO {
    @ManyToOne
    private Song song;

    @ManyToOne
    private Playlist playlist;

    private String description;

    private String imageUrl;

    private String audioUrl;
}

