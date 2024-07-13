package dbp.proyecto.song.infrastructure;

import dbp.proyecto.song.domain.Song;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
    Song findByTitle(String title);

    Page<Song> findByTitleNormalizedContaining(String titleNormalized, Pageable pageable);

    Page<Song> findByGenreContaining(String genre, Pageable pageable);

    Page<Song> findByArtistsNameContaining(String artistNameNormalized, Pageable pageable);

    List<Song> findByAlbumId(Long albumId);
}
