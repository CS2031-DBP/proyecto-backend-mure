package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostResponseDTO {
    @Valid
    private Song song;
    @Valid
    private Playlist playlist;
    @NotBlank
    private String description;
    private String imageUrl;
    private String audioUrl;
}
