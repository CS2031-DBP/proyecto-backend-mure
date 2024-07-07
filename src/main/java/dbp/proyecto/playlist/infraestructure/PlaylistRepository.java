package dbp.proyecto.playlist.infraestructure;

import dbp.proyecto.playlist.domain.Playlist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByName(String name);
    Page<Playlist> findByUserId(Long userId, Pageable pageable);}
