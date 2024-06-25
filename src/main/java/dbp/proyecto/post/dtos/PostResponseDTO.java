package dbp.proyecto.post.dtos;

import lombok.Data;

@Data
public class PostResponseDTO {
    private Long id;
    private String owner;
    private Long ownerId;
    private String songTitle;
    private String albumTitle;
    private Integer likes;
    private String description;
    private String imageUrl;
    private String audioUrl;
}
