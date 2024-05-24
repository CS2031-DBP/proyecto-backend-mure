package dbp.proyecto.post.dtos;

import lombok.Data;

@Data
public class PostBodyDTO {
    private Long userId;
    private Long songId;
    private Long albumId;
    private String description;
    private String imageUrl;
    private String audioUrl;
}

