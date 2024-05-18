package dbp.proyecto.artist.domain;

import dbp.proyecto.artist.dto.ArtistDTO;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, ModelMapper modelMapper) {
        this.artistRepository = artistRepository;
        this.modelMapper = modelMapper;
    }

    public List<ArtistDTO> findAll() {
        return artistRepository.findAll()
                .stream()
                .map(artist -> modelMapper.map(artist, ArtistDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<ArtistDTO> findById(Long id) {
        return artistRepository.findById(id)
                .map(artist -> modelMapper.map(artist, ArtistDTO.class));
    }

    public ArtistDTO save(ArtistDTO artistDto) {
        Artist artist = modelMapper.map(artistDto, Artist.class);
        Artist savedArtist = artistRepository.save(artist);
        return modelMapper.map(savedArtist, ArtistDTO.class);
    }

    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}
