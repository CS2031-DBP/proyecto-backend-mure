package dbp.proyecto.artist.infrastructure;

import dbp.proyecto.artist.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Artist findByName(String name);
    Set<Artist> findByVerifiedTrue();
}


