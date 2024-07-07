package dbp.proyecto.artist.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.dto.ArtistInfoForSongDto;
import dbp.proyecto.artist.dto.ArtistRequestDto;
import dbp.proyecto.artist.dto.ArtistResponseDto;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final SongRepository songRepository;

    private final AlbumRepository albumRepository;

    private final ModelMapper modelMapper;

    private ArtistResponseDto getArtistResponseDto(Artist artist) {
        ArtistResponseDto artistResponseDto = modelMapper.map(artist, ArtistResponseDto.class);
        List<String> albumTitles = artist.getAlbums().stream()
                .map(Album::getTitle)
                .collect(Collectors.toList());
        artistResponseDto.setAlbumsTitles(albumTitles);
        List<String> songTitles = artist.getSongs().stream()
                .map(Song::getTitle)
                .collect(Collectors.toList());
        artistResponseDto.setSongTitles(songTitles);
        artistResponseDto.setAlbumsIds(artist.getAlbums().stream().map(Album::getId).collect(Collectors.toList()));
        artistResponseDto.setSongsIds(artist.getSongs().stream().map(Song::getId).collect(Collectors.toList()));
        return artistResponseDto;
    }

    public ArtistResponseDto getArtistById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return getArtistResponseDto(artist);
    }

    public List<ArtistResponseDto> findVerifiedArtists() {
        List<Artist> artists = artistRepository.findByVerifiedTrue();

        return artists.stream().map(this::getArtistResponseDto).collect(Collectors.toList());
    }

    public Page<ArtistResponseDto> getArtistsByName(String nameNormalized, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return artistRepository.findByNameNormalizedContaining(nameNormalized, pageable).map(artist -> modelMapper.map(artist, ArtistResponseDto.class));
    }

    public List<ArtistInfoForSongDto> getArtistsBySongId(Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return song.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistInfoForSongDto.class))
                .collect(Collectors.toList());
    }

    public List<ArtistInfoForSongDto> getArtistsBySongTitle(String songTitle) {
        Song song = songRepository.findByTitle(songTitle);

        return song.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistInfoForSongDto.class))
                .collect(Collectors.toList());
    }

    public List<ArtistResponseDto> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(this::getArtistResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createArtists(List<ArtistRequestDto> artistRequestDtos) {
        List<Artist> artists = new ArrayList<>();

        for (ArtistRequestDto artistRequestDto : artistRequestDtos) {
            Artist artist = modelMapper.map(artistRequestDto, Artist.class);
            artists.add(artist);
        }
        artistRepository.saveAll(artists);
    }

    public void updateArtistInfo(Long id, ArtistRequestDto updatedArtist) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        if (updatedArtist.getName() != null) {
            artist.setName(updatedArtist.getName());
        }
        if (updatedArtist.getDescription() != null) {
            artist.setDescription(updatedArtist.getDescription());
        }
        if (updatedArtist.getBirthDate() != null) {
            artist.setBirthDate(updatedArtist.getBirthDate());
        }
        if (updatedArtist.getVerified() != null) {
            artist.setVerified(updatedArtist.getVerified());
        }
        artistRepository.save(artist);
    }

    @Transactional
    public void deleteArtist(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        for (Album album : artist.getAlbums()) {
            songRepository.deleteAll(album.getSongs());
            albumRepository.delete(album);
        }
        artistRepository.delete(artist);
    }
}
