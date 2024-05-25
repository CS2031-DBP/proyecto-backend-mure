package dbp.proyecto.artist.domain;

import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.dto.ArtistBodyDTO;
import dbp.proyecto.artist.dto.ArtistInfoForSongDTO;
import dbp.proyecto.artist.dto.ArtistResponseDTO;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;
    private final SongRepository songRepository;
    private final AlbumRepository albumRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, SongRepository songRepository, AlbumRepository albumRepository, ModelMapper modelMapper) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
        this.albumRepository = albumRepository;
        this.modelMapper = modelMapper;
    }

    private ArtistResponseDTO getArtistResponseDTO(Artist artist) {
        ArtistResponseDTO artistResponseDTO = modelMapper.map(artist, ArtistResponseDTO.class);

        List<String> albumTitles = artist.getAlbums().stream()
                .map(Album::getTitle)
                .collect(Collectors.toList());
        artistResponseDTO.setAlbumsTitles(albumTitles);

        List<String> songTitles = artist.getSongs().stream()
                .map(Song::getTitle)
                .collect(Collectors.toList());
        artistResponseDTO.setSongTitles(songTitles);

        return artistResponseDTO;
    }

    public ArtistResponseDTO getArtistById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return getArtistResponseDTO(artist);
    }

    public List<ArtistResponseDTO> findVerifiedArtists() {
        List<Artist> artists = artistRepository.findByVerifiedTrue();

        return artists.stream().map(this::getArtistResponseDTO).collect(Collectors.toList());
    }

    public ArtistResponseDTO getArtistByName(String name) {
        Artist artist = artistRepository.findByName(name);
        if (artist == null) {
            throw new ResourceNotFoundException("Artist not found");
        }
        return getArtistResponseDTO(artist);
    }

    public List<ArtistInfoForSongDTO> getArtistsBySongId(Long songId) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return song.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistInfoForSongDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArtistInfoForSongDTO> getArtistsBySongTitle(String songTitle) {
        Song song = songRepository.findByTitle(songTitle);
        return song.getArtists().stream()
                .map(artist -> modelMapper.map(artist, ArtistInfoForSongDTO.class))
                .collect(Collectors.toList());
    }

    public List<ArtistResponseDTO> getAllArtists() {
        List<Artist> artists = artistRepository.findAll();
        return artists.stream()
                .map(this::getArtistResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public void createArtists(List<ArtistBodyDTO> artistBodyDTOs) {
        List<Artist> artists = new ArrayList<>();

        for (ArtistBodyDTO artistBodyDTO : artistBodyDTOs) {
            Artist artist = modelMapper.map(artistBodyDTO, Artist.class);
            artists.add(artist);
        }
        artistRepository.saveAll(artists);
    }

    public void updateArtistInfo(Long id, ArtistBodyDTO updatedArtist) {
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
