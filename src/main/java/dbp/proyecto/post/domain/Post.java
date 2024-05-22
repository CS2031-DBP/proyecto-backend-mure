package dbp.proyecto.post.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;

@Data
@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String description;
    private String imageUrl;
    private String audioUrl;
    private LocalDateTime createdAt;
    @NotNull
    @Min(0)
    private Integer likes;
    @ManyToOne
    private Song song;
    @ManyToOne
    private Album album;
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}