package dbp.proyecto.post.dtos;

public class PostResponseDTO {
    private String description;

    private String imageurl;

    private String audioUrl;

    public PostResponseDTO(String description, String imageurl, String audioUrl) {
        this.description = description;
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}
