package dbp.proyecto.album.infrastructure;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.domain.AlbumService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Long> {
    List<Album> findByArtistId(Long artistId);
    Album findByTitle(String title);
}
