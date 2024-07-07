package dbp.proyecto.post.dtos;

import dbp.proyecto.album.dto.AlbumInfoForPostDTO;
import dbp.proyecto.song.dto.SongInfoForPostDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class PostResponseDto {
    private Long id;
    private String owner;
    private Long ownerId;
    private String profileImage;
    private LocalDateTime CreatedAt;
    private SongInfoForPostDto song;
    private AlbumInfoForPostDTO album;
    private Integer likes;
    private String description;
    private String imageUrl;
    private String audioUrl;
    private Set<Long> likedByUserIds;
}
