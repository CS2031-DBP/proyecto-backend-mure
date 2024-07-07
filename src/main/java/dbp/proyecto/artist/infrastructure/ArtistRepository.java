package dbp.proyecto.artist.infrastructure;

import dbp.proyecto.artist.domain.Artist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Page<Artist> findByNameNormalizedContaining(String nameNormalized, Pageable pageable);

    List<Artist> findByVerifiedTrue();
}
