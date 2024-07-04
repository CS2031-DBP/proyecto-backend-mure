package dbp.proyecto.post.infrastructure;

import dbp.proyecto.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findBySongId(Long songId);
    List<Post> findByAlbumId(Long albumId);
    List<Post> findByUserId(Long userId);
    Page<Post> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
