package dbp.proyecto.artist.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.dto.ArtistBodyDTO;
import dbp.proyecto.artist.dto.ArtistBodyInfoDTO;
import dbp.proyecto.artist.dto.ArtistResponseDTO;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.tablasIntermedias.artistAlbum.ArtistAlbum;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistService {
    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    private final SongRepository songRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public ArtistService(ArtistRepository artistRepository, AlbumRepository albumRepository, SongRepository songRepository, ModelMapper modelMapper) {
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.modelMapper = modelMapper;
    }

    public List<ArtistResponseDTO> findAllArtists() {
        List<Artist> artists = artistRepository.findAll();

        if(artists.isEmpty()) {
            throw new ResourceNotFoundException("No artists found");
        }

        return artists.stream()
                .map(artist -> modelMapper.map(artist, ArtistResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ArtistResponseDTO findById(Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        return modelMapper.map(artist, ArtistResponseDTO.class);
    }

    public List<String> createArtists(List<ArtistBodyDTO> artistBodyDTOs) {
        List<String> artistUrls = new ArrayList<>();

        for (ArtistBodyDTO artistBodyDTO : artistBodyDTOs) {
            Artist artist = new Artist();
            artist.setName(artistBodyDTO.getName());
            artist.setDescription(artistBodyDTO.getDescription());
            artist.setBirthDate(artistBodyDTO.getBirthDate());
            artist.setVerified(artistBodyDTO.getVerified());


            List<Long> albumIds = artistBodyDTO.getArtistAlbums() != null ? artistBodyDTO.getArtistAlbums() : new ArrayList<>();
            for (Long albumId : albumIds) {
                Album album = albumRepository.findById(albumId).orElseThrow(() -> new ResourceNotFoundException("Album not found"));

                ArtistAlbum artistAlbum = new ArtistAlbum();
                artistAlbum.setArtist(artist);
                artistAlbum.setAlbum(album);

                artist.getArtistAlbums().add(artistAlbum);
            }

            List<Long> songIds = artistBodyDTO.getArtistSongs() != null ? artistBodyDTO.getArtistSongs() : new ArrayList<>();
            for (Long songId : songIds) {
                Song song = songRepository.findById(songId).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

                ArtistSongs artistSongs = new ArtistSongs();
                artistSongs.setArtist(artist);
                artistSongs.setSong(song);

                artist.getSongs().add(artistSongs);
            }

            artistRepository.save(artist);
            artistUrls.add("/artist/" + artist.getId());
        }

        return artistUrls;
    }



    public void updateArtistInfo(Long id, ArtistBodyInfoDTO updatedArtistResponseDTO) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        modelMapper.map(updatedArtistResponseDTO, artist);

        artistRepository.save(artist);
    }

    public void deleteArtist(Long id) {
        artistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        artistRepository.deleteById(id);
    }
}
