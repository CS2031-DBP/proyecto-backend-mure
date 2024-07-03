package dbp.proyecto.song.infrastructure;

import dbp.proyecto.song.domain.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long >{
    @Query("SELECT s FROM Song s WHERE normalize_text(s.title) = normalize_text(:title)")
    Song findByTitle(@Param("title") String title);
    List<Song> findByGenre(String genre);
    List<Song> findByAlbumId(Long albumId);
}



