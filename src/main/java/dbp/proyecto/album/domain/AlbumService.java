package dbp.proyecto.album.domain;

import dbp.proyecto.album.dto.AlbumDTO;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, ModelMapper modelMapper) {
        this.albumRepository = albumRepository;
        this.modelMapper = modelMapper;
    }

    public List<AlbumDTO> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(album -> modelMapper.map(album, AlbumDTO.class))
                .collect(Collectors.toList());
    }

    public Optional<AlbumDTO> findById(Long id) {
        return albumRepository.findById(id).map(album -> modelMapper.map(album, AlbumDTO.class));
    }

    public AlbumDTO save(AlbumDTO albumDto) {
        Album album = modelMapper.map(albumDto, Album.class);
        Album savedAlbum = albumRepository.save(album);
        return modelMapper.map(savedAlbum, AlbumDTO.class);
    }

    public void deleteById(Long id) {
        albumRepository.deleteById(id);
    }
}
