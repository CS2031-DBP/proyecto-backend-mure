package dbp.proyecto.album.domain;

import dbp.proyecto.album.dto.AlbumDto;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    @Autowired
    private AlbumRepository albumRepository;
    @Autowired
    private ModelMapper modelMapper;

    public List<AlbumDto> findAll() {
        return albumRepository.findAll()
                .stream()
                .map(album -> modelMapper.map(album, AlbumDto.class))
                .collect(Collectors.toList());
    }

    public Optional<AlbumDto> findById(Long id) {
        return albumRepository.findById(id).map(album -> modelMapper.map(album, AlbumDto.class));
    }

    public AlbumDto save(AlbumDto albumDto) {
        Album album = modelMapper.map(albumDto, Album.class);
        Album savedAlbum = albumRepository.save(album);
        return modelMapper.map(savedAlbum, AlbumDto.class);
    }

    public void deleteById(Long id) {
        albumRepository.deleteById(id);
    }
}
