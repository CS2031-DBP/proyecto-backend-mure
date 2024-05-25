package dbp.proyecto.post.dtos;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;

import lombok.Data;

@Data
public class PostResponseDTO {
    private Long id;
    private String owner;
    private Song song;
    private Album album;
    private Integer likes;
    private String description;
    private String imageUrl;
    private String audioUrl;
}
