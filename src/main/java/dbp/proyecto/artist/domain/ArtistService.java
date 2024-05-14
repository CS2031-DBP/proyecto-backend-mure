package dbp.proyecto.artist.domain;

import dbp.proyecto.artist.infrastructure.ArtistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {
    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<Artist> findAll() {
        return artistRepository.findAll();
    }

    public Artist findById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    public Artist findByName(String name) {
        return artistRepository.findByName(name);
    }
}
