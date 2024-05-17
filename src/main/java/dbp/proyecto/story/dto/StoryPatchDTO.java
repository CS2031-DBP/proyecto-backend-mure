package dbp.proyecto.story.dto;

import jakarta.validation.constraints.Size;

public class StoryPatchDTO {
    @Size(max = 200)
    private String text;

    public StoryPatchDTO(String text) {
        this.text = text;
    }

    public StoryPatchDTO() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
