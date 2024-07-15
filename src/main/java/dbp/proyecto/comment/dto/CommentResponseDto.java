package dbp.proyecto.comment.dto;

import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private String content;
    private Long userId;
    private String nickname;
    private Long postId;
}
