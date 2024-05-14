package dbp.proyecto.story.infrastructure;


import dbp.proyecto.story.domain.Story;
import dbp.proyecto.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story, Long>{
}
