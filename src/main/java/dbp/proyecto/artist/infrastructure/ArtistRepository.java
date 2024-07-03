package dbp.proyecto.artist.infrastructure;

import dbp.proyecto.artist.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    @Query("SELECT a FROM Artist a WHERE normalize_text(a.name) = normalize_text(:name)")
    Artist findByName(@Param("name") String name);
    List<Artist> findByVerifiedTrue();
}


