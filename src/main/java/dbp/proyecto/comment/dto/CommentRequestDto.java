package dbp.proyecto.comment.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String content;
    private Long userId;
    private Long postId;
}
