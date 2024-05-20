package dbp.proyecto.album.domain;

import dbp.proyecto.album.dto.AlbumBodyDTO;
import dbp.proyecto.album.dto.AlbumResponseDTO;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.tablasIntermedias.artistAlbum.ArtistAlbum;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final ModelMapper modelMapper;

    private final ArtistRepository artistRepository;

    private final SongRepository songRepository;

    @Autowired
    public AlbumService(AlbumRepository albumRepository, ModelMapper modelMapper, ArtistRepository artistRepository, SongRepository songRepository) {
        this.albumRepository = albumRepository;
        this.modelMapper = modelMapper;
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    public List<AlbumResponseDTO> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();

        if(albums.isEmpty()){
            throw new ResourceNotFoundException("Albums not found");
        }

        return albums.stream()
                .map(album -> modelMapper.map(album, AlbumResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AlbumResponseDTO getById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        return modelMapper.map(album, AlbumResponseDTO.class);
    }

    public String createAlbum(AlbumBodyDTO albumBodyDto) {
        Album album = new Album();
        album.setTitle(albumBodyDto.getTitle());
        album.setDescription(albumBodyDto.getDescription());

        Duration duration = albumBodyDto.getDuration();
        if (duration != null) {
            album.setDurationSeconds(duration.getSeconds());
        }

        List<Long> artistIds = albumBodyDto.getArtistsIds();
        if (artistIds != null && !artistIds.isEmpty()) {
            for (Long artistId : artistIds) {
                Artist artist = artistRepository.findById(artistId)
                        .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
                ArtistAlbum artistAlbum = new ArtistAlbum();
                artistAlbum.setArtist(artist);
                artistAlbum.setAlbum(album);
                album.getArtistAlbums().add(artistAlbum);
            }
        }

        List<Long> songIds = albumBodyDto.getSongsIds();
        if (songIds != null && !songIds.isEmpty()) {
            for (Long songId : songIds) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                song.setAlbum(album);
                album.getSongs().add(song);
            }
        }

        Album savedAlbum = albumRepository.save(album);
        return savedAlbum.getId().toString();
    }


    public void updateAlbum(Long id, AlbumBodyDTO updatedAlbumBodyDTO) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        album.setTitle(updatedAlbumBodyDTO.getTitle());
        album.setDescription(updatedAlbumBodyDTO.getDescription());

        Duration duration = updatedAlbumBodyDTO.getDuration();
        if (duration != null) {
            album.setDurationSeconds(duration.getSeconds());
        } else {
            album.setDurationSeconds(null);
        }

        albumRepository.save(album);
    }


    public void deleteAlbum(Long id) {
        albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));

        albumRepository.deleteById(id);
    }
}
