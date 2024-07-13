package dbp.proyecto.playlist.dtos;


import lombok.Data;

import java.util.List;

@Data
public class PlaylistResponseDTO {
    private Long id;
    private String name;
    private Long userId;
    private String nickname;
    private List<Long> songsIds;
}
