package dbp.proyecto.playlist.infrastructure;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.song.dto.SongsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    List<Playlist> findByAuthorsName(String authorName);


    List<Playlist> findByName(String name);
}
