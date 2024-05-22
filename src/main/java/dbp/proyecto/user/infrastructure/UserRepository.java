package dbp.proyecto.user.infrastructure;

import dbp.proyecto.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    List<User> findByAgeBetween(int minAge, int maxAge);
    List<User> findByCreatedAtBefore(LocalDateTime date);

}