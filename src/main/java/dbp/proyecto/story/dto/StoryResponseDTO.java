package dbp.proyecto.story.dto;

import dbp.proyecto.song.domain.Song;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryResponseDTO {
    private String owner;
    private LocalDateTime createdAt;
    private String videoUrl;
    private String text;
    private Integer likes;
    private Song song;
}
