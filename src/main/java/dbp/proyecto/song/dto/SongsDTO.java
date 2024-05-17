package dbp.proyecto.song.dto;

import jakarta.persistence.ElementCollection;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Duration;
import java.util.List;


@Data
public class SongsDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String title;

    @NotBlank
    @Size(min = 1, max = 50)
    private String album;

    @ElementCollection
    private List<String> artists;

    @NotBlank
    private Duration duration;

    @NotBlank
    private String coverImage;
}
