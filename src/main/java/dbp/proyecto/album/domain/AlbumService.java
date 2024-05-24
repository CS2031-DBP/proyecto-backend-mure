package dbp.proyecto.album.domain;

import dbp.proyecto.album.dto.AlbumBodyDTO;
import dbp.proyecto.album.dto.AlbumInfoForArtistDTO;
import dbp.proyecto.album.dto.AlbumResponseDTO;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    private AlbumResponseDTO getAlbumResponseDTO(Album album) {
        AlbumResponseDTO albumResponseDTO = modelMapper.map(album, AlbumResponseDTO.class);
        albumResponseDTO.setArtistName(album.getArtist().getName());
        albumResponseDTO.setSongsNames(album.getSongs().stream().map(Song::getTitle).collect(Collectors.toList()));
        return albumResponseDTO;
    }

    public AlbumResponseDTO getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        return getAlbumResponseDTO(album);
    }

    public AlbumResponseDTO getAlbumByTitle(String title) {
        Album album = albumRepository.findByTitle(title);
        return getAlbumResponseDTO(album);
    }

    public List<AlbumInfoForArtistDTO> getAlbumsByArtistId(Long artistId) {
        List<Album> albums = albumRepository.findByArtistId(artistId);
        return albums.stream()
                .map(album -> modelMapper.map(album, AlbumInfoForArtistDTO.class))
                .collect(Collectors.toList());
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

    @Transactional
    public void createAlbums(List<AlbumBodyDTO> albumBodyDtos) {
        Artist a = new Artist();
        for (AlbumBodyDTO albumBodyDto : albumBodyDtos) {
            Album album = new Album();
            album.setTitle(albumBodyDto.getTitle());
            album.setDescription(albumBodyDto.getDescription());
            album.setReleaseDate(albumBodyDto.getReleaseDate());

            Long artistId = albumBodyDto.getArtistId();
            if (artistId == null) {
                throw new IllegalArgumentException("ArtistId is required");
            }
            Artist artist = artistRepository.findById(artistId)
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            album.setArtist(artist);
            List<Long> songIds = albumBodyDto.getSongsIds();
            if (songIds == null || songIds.isEmpty()) {
                throw new IllegalArgumentException("At least one songId is required");
            }
            for (Long songId : songIds) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                album.getSongs().add(song);
            }
            album.calculateTotalDuration();
            album.calculateSongsCount();
            albumRepository.save(album);

            artist.getAlbums().add(album);
            a = artistRepository.save(artist);


            for (Song song : album.getSongs()) {
                song.setAlbum(album);
                songRepository.save(song);
            }
        }
    }

    public void updateAlbum(Long id, AlbumBodyDTO updatedAlbumBodyDTO) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        if(updatedAlbumBodyDTO.getTitle() != null) {
            album.setTitle(updatedAlbumBodyDTO.getTitle());
        }
        if(updatedAlbumBodyDTO.getDescription() != null) {
            album.setDescription(updatedAlbumBodyDTO.getDescription());
        }
        if (updatedAlbumBodyDTO.getReleaseDate() != null) {
            album.setReleaseDate(updatedAlbumBodyDTO.getReleaseDate());
        }
        if (updatedAlbumBodyDTO.getArtistId() != null) {
            Artist artist = artistRepository.findById(updatedAlbumBodyDTO.getArtistId())
                    .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
            album.setArtist(artist);
        }
        if (updatedAlbumBodyDTO.getSongsIds() != null) {
            List<Song> songs = updatedAlbumBodyDTO.getSongsIds().stream()
                    .map(songId -> songRepository.findById(songId)
                            .orElseThrow(() -> new ResourceNotFoundException("Song not found")))
                    .collect(Collectors.toList());
            album.setSongs(songs);
        }

        albumRepository.save(album);
    }

    @Transactional
    public void deleteAlbum(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        Artist artist = album.getArtist();

        List<Song> songs = album.getSongs();
        for (Song song : songs) {
            song.setAlbum(null);
            songRepository.save(song);
        }

        // Eliminar el álbum de la lista de álbumes del artista y guardar el artista
        artist.getAlbums().remove(album);
        artistRepository.save(artist);

        // Finalmente eliminar el álbum
        albumRepository.delete(album);
    }



}
