package dbp.proyecto.artist.infrastructure;

import dbp.proyecto.artist.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
    List<Artist> findByVerifiedTrue();
    List<Artist> findBySongsId(Long songId);
}
