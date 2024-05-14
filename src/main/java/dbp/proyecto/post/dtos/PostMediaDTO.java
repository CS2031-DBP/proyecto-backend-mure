package dbp.proyecto.post.dtos;

public class PostMediaDTO {

    private String imageurl;

    private String audioUrl;

    public PostMediaDTO(String imageurl, String audioUrl) {
        this.imageurl = imageurl;
        this.audioUrl = audioUrl;
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
