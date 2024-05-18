package dbp.proyecto.playlist.infrastructure;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByUsers_Id(Long userId);
    List<Playlist> findBySongs_Id(Long songId);
    List<Playlist> findByTitleContaining(String title);
    Optional<Playlist> findByName(String name);
}