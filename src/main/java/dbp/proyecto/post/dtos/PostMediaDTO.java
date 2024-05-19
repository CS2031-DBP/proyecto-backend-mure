package dbp.proyecto.post.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostMediaDTO {

    private String imageUrl;

    private String audioUrl;

    public PostMediaDTO(String imageUrl, String audioUrl) {
        this.imageUrl = imageUrl;
        this.audioUrl = audioUrl;
    }

}
