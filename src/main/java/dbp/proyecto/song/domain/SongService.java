package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.dto.SongInfoForAlbumDto;
import dbp.proyecto.song.dto.SongInfoForArtistDto;
import dbp.proyecto.song.dto.SongRequestDto;
import dbp.proyecto.song.dto.SongResponseDto;
import dbp.proyecto.song.infrastructure.SongRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SongService {
    private final SongRepository songRepository;

    private final ArtistRepository artistRepository;

    private final AlbumRepository albumRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    private SongResponseDto getSongResponseDTO(Song song) {
        SongResponseDto songResponseDTO = modelMapper.map(song, SongResponseDto.class);
        if (song.getAlbum() != null) {
            songResponseDTO.setAlbumTitle(song.getAlbum().getTitle());
        }
        List<String> artistsNames = song.getArtists().stream()
                .map(Artist::getName)
                .collect(Collectors.toList());
        songResponseDTO.setArtistsNames(artistsNames);
        return songResponseDTO;
    }

    private List<SongInfoForArtistDto> getSongInfoForArtistDTOS(Artist artist) {
        List<Song> songs = artist.getSongs();

        return songs.stream().map(song -> {
            SongInfoForArtistDto songInfoForArtistDTO = modelMapper.map(song, SongInfoForArtistDto.class);
            if (song.getAlbum() != null) {
                songInfoForArtistDTO.setAlbumTitle(song.getAlbum().getTitle());
            }
            return songInfoForArtistDTO;
        }).collect(Collectors.toList());
    }

    public SongResponseDto getSongById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return getSongResponseDTO(song);
    }

    public Page<SongResponseDto> getSongsByTitle(String title, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs = songRepository.findByTitleNormalizedContaining(title, pageable);
        if (songs.isEmpty()) throw new ResourceNotFoundException("Songs not found by that title");
        return songs.map(this::getSongResponseDTO);
    }

    public Page<SongResponseDto> getSongsByGenre(String genre, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs = songRepository.findByGenreContaining(genre, pageable);
        if (songs.isEmpty()) throw new ResourceNotFoundException("Songs not found by that genre");
        return songs.map(this::getSongResponseDTO);
    }

    public List<SongInfoForArtistDto> getSongsByArtistId(Long artistId) {
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return getSongInfoForArtistDTOS(artist);
    }

    public Page<SongResponseDto> getSongsByArtistName(String artistNameNormalized, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return songRepository.findByArtistsNameContaining(artistNameNormalized, pageable).map(song -> modelMapper.map(song, SongResponseDto.class));
    }

    public List<SongInfoForAlbumDto> getSongsByAlbumId(Long albumId) {
        List<Song> songs = songRepository.findByAlbumId(albumId);
        if (songs.isEmpty()) {
            throw new ResourceNotFoundException("Album not found");
        }
        return songs.stream().map(song -> {
            SongInfoForAlbumDto songInfoForAlbumDTO = modelMapper.map(song, SongInfoForAlbumDto.class);
            List<String> artistsNames = song.getArtists().stream()
                    .map(Artist::getName)
                    .collect(Collectors.toList());
            songInfoForAlbumDTO.setArtistsNames(artistsNames);
            return songInfoForAlbumDTO;
        }).collect(Collectors.toList());
    }

    public Page<SongResponseDto> getAllSongs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs = songRepository.findAll(pageable);
        return songs.map(this::getSongResponseDTO);
    }

    @Transactional
    public void createSongs(List<SongRequestDto> songRequestDtos) {
        List<Song> songs = new ArrayList<>();
        Random random = new Random();
        for (SongRequestDto songRequestDto : songRequestDtos) {
            Song song = modelMapper.map(songRequestDto, Song.class);
            song.setLink(songRequestDto.getLink());
            int likes = 50000 + random.nextInt(450001);
            song.setLikes(likes);
            int timesPlayed = 50000 + random.nextInt(9550001);
            song.setTimesPlayed(timesPlayed);
            songRepository.save(song);
            List<Artist> artists = artistRepository.findAllById(songRequestDto.getArtistsIds());
            if (artists.isEmpty()) {
                throw new IllegalArgumentException("Minimum one artist is required");
            }
            song.setArtists(artists);
            for (Artist artist : artists) {
                artist.getSongs().add(song);
            }
            artistRepository.saveAll(artists);
            songs.add(song);
        }
        songRepository.saveAll(songs);
    }

    public void updateCoverImage(String coverImage, Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        song.setCoverImage(coverImage);
        songRepository.save(song);
    }

    @Transactional
    public void deleteSong(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        List<Post> posts = postRepository.findBySongId(id);
        for (Post post : posts) {
            post.setSong(null);
            postRepository.save(post);
        }

        for (Artist artist : song.getArtists()) {
            artist.getSongs().remove(song);
            artistRepository.save(artist);
        }
        Album album = song.getAlbum();
        if (album != null && album.getSongs() != null) {
            album.getSongs().remove(song);
            albumRepository.save(album);
        }

        songRepository.delete(song);
    }
}
