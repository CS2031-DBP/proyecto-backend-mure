package dbp.proyecto.story.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoryResponseDTO {

    @Column(nullable = false)
    private String videoUrl;

    @Size(max = 200)
    private String text;

    public StoryResponseDTO(String videoUrl, String text) {
        this.videoUrl = videoUrl;
        this.text = text;
    }

    public StoryResponseDTO() {
    }

}
