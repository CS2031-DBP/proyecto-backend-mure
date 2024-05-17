package dbp.proyecto.playlistSongs;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistSongsRepository extends JpaRepository<PlaylistSongs, PlaylistSongId> {
}
