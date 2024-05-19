package dbp.proyecto.user.infrastructure;

import dbp.proyecto.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByEmail(String email);
    Optional<User> findUserByName(String name);
    Optional<User> findUserById(Long userId);
}