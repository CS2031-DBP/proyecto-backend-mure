package dbp.proyecto.album.infrastructure;

import dbp.proyecto.album.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);
    List<Album> findByArtistName(String artistName);
    @Query("SELECT a FROM Album a WHERE normalize_text(a.title) = normalize_text(:title)")
    Album findByTitle(@Param("title") String title);
}
