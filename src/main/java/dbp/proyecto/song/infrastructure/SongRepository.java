package dbp.proyecto.song.infrastructure;

import dbp.proyecto.song.domain.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SongRepository extends JpaRepository<Song,Long >{
    @Query("SELECT s FROM Song s WHERE normalize_text(s.title) = normalize_text(:title)")
    Song findByTitle(@Param("title") String title);
    @Query("SELECT s FROM Song s WHERE normalize_text(s.title) = normalize_text(:title)")
    Page<Song> findByTitle(String title, Pageable pageable);
    @Query("SELECT s FROM Song s WHERE normalize_text(s.genre) = normalize_text(:genre)")
    Page<Song> findByGenre(String genre, Pageable pageable);
    List<Song> findByGenre(String genre);
    List<Song> findByAlbumId(Long albumId);
}



