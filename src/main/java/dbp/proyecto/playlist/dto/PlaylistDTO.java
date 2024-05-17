package dbp.proyecto.playlist.dto;

import dbp.proyecto.playlistSongs.PlaylistSongs;
import dbp.proyecto.playlistUser.PlaylistUser;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PlaylistDTO {

    @NotBlank
    @Size(min = 1, max = 50)
    private String name;

    @NotBlank
    private List<PlaylistUser> authors = new ArrayList<>();

    private List<PlaylistSongs> songs = new ArrayList<>();
}
