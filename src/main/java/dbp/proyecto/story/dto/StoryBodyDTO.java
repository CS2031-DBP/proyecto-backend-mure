package dbp.proyecto.story.dto;


import lombok.Data;

@Data
public class StoryBodyDTO {
    private Long userId;
    private Long songId;
    private String videoUrl;
    private String text;
}
