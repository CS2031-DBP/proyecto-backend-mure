package dbp.proyecto.comment.domain;

import dbp.proyecto.comment.dto.CommentRequestDto;
import dbp.proyecto.comment.dto.CommentResponseDto;
import dbp.proyecto.comment.infrastructure.CommentRepository;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.user.domain.User;
import dbp.proyecto.user.infrastructure.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    private final  PostRepository postRepository;

    private final UserRepository userRepository;

    public void createComment(CommentRequestDto commentRequestDto) {
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setCreatedAt(LocalDateTime.now());
        Post post = postRepository.findById(commentRequestDto.getPostId()).
                orElseThrow(() -> new RuntimeException("Post not found"));
        comment.setPost(post);
        User user = userRepository.findById(commentRequestDto.getUserId()).
                orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);
        commentRepository.save(comment);
    }

    public Page<CommentResponseDto> getCommentsByPostId(Long postId, int page, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(page);

        return commentRepository.findByPostId(postId, pageable).map(comment -> {
            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setId(comment.getId());
            commentResponseDto.setContent(comment.getContent());
            commentResponseDto.setPostId(comment.getPost().getId());
            commentResponseDto.setUserId(comment.getUser().getId());
            commentResponseDto.setNickname(comment.getUser().getNickname());
            return commentResponseDto;
        });
    }


    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).
                orElseThrow(() -> new RuntimeException("Comment not found"));
        Post post = comment.getPost();
        post.getComments().remove(comment);
        postRepository.save(post);
        User user = comment.getUser();
        user.getComments().remove(comment);
        userRepository.save(user);
        commentRepository.delete(comment);
    }

}
