package dbp.proyecto.song.infrastructure;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.dto.SongsDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface SongRepository extends JpaRepository<Song,Long >{
    Optional<Song> findByTitle(String title);
    List<Song> findByArtists(String artist);
    List<Song> findByGenre(String genre);
}


