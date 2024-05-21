package dbp.proyecto.story.dto;


import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.domain.Song;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StoryBodyDTO {
    private Song song;

    private String videoUrl;

    private String text;
}
