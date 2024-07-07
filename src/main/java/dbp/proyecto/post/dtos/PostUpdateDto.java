package dbp.proyecto.post.dtos;

import lombok.Data;

@Data
public class PostUpdateDto {
    private String imageUrl;
    private String audioUrl;
    private String description;
}
