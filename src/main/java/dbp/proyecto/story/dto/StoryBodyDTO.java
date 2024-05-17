package dbp.proyecto.story.dto;

import dbp.proyecto.playlist.Playlist;
import dbp.proyecto.song.Song;
import jakarta.persistence.ManyToOne;

public class StoryBodyDTO {
    private Song song;

    private Playlist playlist;

    private String videoUrl;

    private String text;
}
