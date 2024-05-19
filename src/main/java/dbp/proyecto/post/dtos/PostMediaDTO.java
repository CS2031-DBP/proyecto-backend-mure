package dbp.proyecto.post.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PostMediaDTO {
    @NotBlank
    private String imageUrl;
    @NotBlank
    private String audioUrl;
}
