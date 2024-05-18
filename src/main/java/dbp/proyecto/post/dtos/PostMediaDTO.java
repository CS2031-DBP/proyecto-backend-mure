package dbp.proyecto.post.dtos;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostMediaDTO {

    private String imageurl;

    private String audioUrl;

    public PostMediaDTO(String imageurl, String audioUrl) {
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
    }

}
