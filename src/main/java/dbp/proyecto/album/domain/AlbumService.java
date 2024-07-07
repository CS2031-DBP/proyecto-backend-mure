package dbp.proyecto.album.domain;

import dbp.proyecto.album.dto.AlbumInfoForArtistDto;
import dbp.proyecto.album.dto.AlbumRequestDto;
import dbp.proyecto.album.dto.AlbumResponseDto;
import dbp.proyecto.album.dto.AlbumUpdateDto;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlbumService {
    private final AlbumRepository albumRepository;

    private final ModelMapper modelMapper;

    private final ArtistRepository artistRepository;

    private final SongRepository songRepository;

    private AlbumResponseDto getAlbumResponseDto(Album album) {
        AlbumResponseDto albumResponseDto = modelMapper.map(album, AlbumResponseDto.class);
        albumResponseDto.setArtistName(album.getArtist().getName());
        albumResponseDto.setSongsTitles(album.getSongs().stream()
                .map(Song::getTitle)
                .collect(Collectors.toList()));
        albumResponseDto.setSongsIds(album.getSongs().stream().map(Song::getId).collect(Collectors.toList()));
        albumResponseDto.setArtistId(album.getArtist().getId());
        return albumResponseDto;
    }

    public AlbumResponseDto getAlbumById(Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        return getAlbumResponseDto(album);
    }

    public Page<AlbumResponseDto> getAlbumsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return albumRepository.findByTitleNormalizedContaining(title, pageable).map(album -> modelMapper.map(album,
                AlbumResponseDto.class));
    }

    public List<AlbumInfoForArtistDto> getAlbumsByArtistId(Long artistId) {
        List<Album> albums = albumRepository.findByArtistId(artistId);
        return albums.stream()
                .map(album -> modelMapper.map(album, AlbumInfoForArtistDto.class))
                .collect(Collectors.toList());
    }

    public List<AlbumInfoForArtistDto> getAlbumsByArtistName(String artistName, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Album> albums = albumRepository.findByArtistNameNormalizedContaining(artistName, pageable);
        return albums.stream()
                .map(album -> modelMapper.map(album, AlbumInfoForArtistDto.class))
                .collect(Collectors.toList());
    }

    public List<AlbumResponseDto> getAllAlbums() {
        List<Album> albums = albumRepository.findAll();
        return albums.stream()
                .map(this::getAlbumResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createsAlbums(List<AlbumRequestDto> albumBodyDTOs) {
        for (AlbumRequestDto albumBodyDto : albumBodyDTOs) {
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
            for (Song song : album.getSongs()) {
                song.setAlbum(album);
                songRepository.save(song);
            }
        }
    }

    public void updateAlbum(Long id, AlbumUpdateDto albumUpdateDTO) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        if (albumUpdateDTO.getTitle() != null) {
            album.setTitle(albumUpdateDTO.getTitle());
        }
        if (albumUpdateDTO.getDescription() != null) {
            album.setDescription(albumUpdateDTO.getDescription());
        }
        if (albumUpdateDTO.getSongsIds() != null) {
            List<Song> songs = album.getSongs();
            for (Song song : songs) {
                song.setAlbum(null);
                songRepository.save(song);
            }
            album.getSongs().clear();
            for (Long songId : albumUpdateDTO.getSongsIds()) {
                Song song = songRepository.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song not found"));
                album.getSongs().add(song);
            }
        }
        album.calculateTotalDuration();
        album.calculateSongsCount();
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

        artist.getAlbums().remove(album);
        artistRepository.save(artist);
        albumRepository.delete(album);
    }
}
