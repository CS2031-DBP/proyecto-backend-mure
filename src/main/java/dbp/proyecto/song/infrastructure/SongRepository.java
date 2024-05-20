package dbp.proyecto.song.infrastructure;

import dbp.proyecto.song.domain.Song;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song,Long >{
    List<Song> findByTitle(String title);
    List<Song> findByArtists (List<ArtistSongs> artists);
    List<Song> findByGenre(String genre);
}


