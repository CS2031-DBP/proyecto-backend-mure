package dbp.proyecto.playlist.dtos;


import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private Long userId;
    private List<Long> songsIds;
}
