package dbp.proyecto.post.dtos;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PostBodyDTO {
    @Valid
    private Song song;
    @Valid
    private Playlist playlist;
    @NotBlank
    private String description;
    private String imageUrl;
    private String audioUrl;
}

