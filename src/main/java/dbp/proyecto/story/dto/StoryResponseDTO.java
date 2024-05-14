package dbp.proyecto.story.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Size;

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

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
