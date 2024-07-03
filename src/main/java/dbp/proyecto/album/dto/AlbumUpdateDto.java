package dbp.proyecto.album.dto;

import lombok.Data;

import java.util.List;

@Data
public class AlbumUpdateDto {
    private String title;
    private String description;
    private List<Long> songsIds;
}
