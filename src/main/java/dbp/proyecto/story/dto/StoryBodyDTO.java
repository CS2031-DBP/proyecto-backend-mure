package dbp.proyecto.story.dto;


import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import lombok.Data;

@Data
public class StoryBodyDTO {
    private Song song;

    private Playlist playlist;

    private String videoUrl;

    private String text;
}
