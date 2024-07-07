package dbp.proyecto.album.infrastructure;

import dbp.proyecto.album.domain.Album;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);

    Page<Album> findByArtistNameNormalizedContaining(String artistNameNormalized, Pageable pageable);

    Page<Album> findByTitleNormalizedContaining(String titleNormalized, Pageable pageable);
}
