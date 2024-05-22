package dbp.proyecto.post.dtos;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;

import lombok.Data;

@Data
public class PostResponseDTO {
    private Song song;
    private Album album;
    private String description;
    private String imageUrl;
    private String audioUrl;
}
