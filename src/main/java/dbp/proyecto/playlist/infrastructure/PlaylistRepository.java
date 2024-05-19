package dbp.proyecto.playlist.infrastructure;

import dbp.proyecto.playlist.domain.Playlist;
import dbp.proyecto.tablasIntermedias.playlistSongs.PlaylistSongId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUsersId(Long userId);
    List<Playlist> findBySongId(Long songId);
    List<Playlist> findByNameContaining(String title);
    List<Playlist> findByAuthorsName(String authorName);
    Optional<Playlist> findByName(String name);
}
