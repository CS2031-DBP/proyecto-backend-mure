package dbp.proyecto.artist.domain;

import dbp.proyecto.artist.dto.ArtistDto;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    @Autowired
    private ArtistRepository artistRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<ArtistDto> findAll() {
        return artistRepository.findAll()
                .stream()
                .map(artist -> modelMapper.map(artist, ArtistDto.class))
                .collect(Collectors.toList());
    }

    public Optional<ArtistDto> findById(Long id) {
        return artistRepository.findById(id)
                .map(artist -> modelMapper.map(artist, ArtistDto.class));
    }

    public ArtistDto save(ArtistDto artistDto) {
        Artist artist = modelMapper.map(artistDto, Artist.class);
        Artist savedArtist = artistRepository.save(artist);
        return modelMapper.map(savedArtist, ArtistDto.class);
    }

    public void deleteById(Long id) {
        artistRepository.deleteById(id);
    }
}
