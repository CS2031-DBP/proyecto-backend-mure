package dbp.proyecto.playlistUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistUserRepository extends JpaRepository<PlaylistUser, PlaylistUserId> {
}