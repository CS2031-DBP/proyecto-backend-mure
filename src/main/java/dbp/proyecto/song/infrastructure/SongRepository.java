package dbp.proyecto.song.infrastructure;

import dbp.proyecto.song.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long >{
    Song findByTitle(String title);
    List<Song> findByGenre(String genre);
    List<Song> findByAlbumId(Long albumId);
}


