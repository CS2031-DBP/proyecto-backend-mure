package dbp.proyecto.story.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StoryPatchDTO {
    @Size(max = 200)
    private String text;

    public StoryPatchDTO(String text) {
        this.text = text;
    }

    public StoryPatchDTO() {
    }

}
