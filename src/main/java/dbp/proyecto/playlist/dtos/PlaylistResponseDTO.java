package dbp.proyecto.playlist.dtos;

import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongs;
import dbp.proyecto.tablasIntermedias.playlistUser.PlaylistUser;

import java.util.List;

public class PlaylistResponseDTO {
    private String name;

    private List<PlaylistUser> users;

    private List<PlaylistSongs> songs;
}
