package dbp.proyecto.story.infrastructure;

import dbp.proyecto.story.domain.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long>{
    List<Story> findBySongId(Long songId);
    List<Story> findByUserId(Long userId);
}
