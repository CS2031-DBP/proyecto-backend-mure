package dbp.proyecto.artist.dto;

import dbp.proyecto.tablasIntermedias.artistAlbum.ArtistAlbum;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ArtistResponseDTO {
    private String name;
    private String description;
    private Date birthDate;
    private List<ArtistAlbum> artistAlbums;
    private List<ArtistSongs> songs;
}
