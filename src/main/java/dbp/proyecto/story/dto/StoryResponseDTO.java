package dbp.proyecto.story.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StoryResponseDTO {
    private Long id;
    private String owner;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private String videoUrl;
    private String text;
    private Integer likes;
    private String songTitle;
}
