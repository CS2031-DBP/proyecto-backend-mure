package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.album.infrastructure.AlbumRepository;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.post.domain.Post;
import dbp.proyecto.post.infrastructure.PostRepository;
import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongInfoForAlbumDTO;
import dbp.proyecto.song.dto.SongInfoForArtistDTO;
import dbp.proyecto.song.dto.SongResponseDTO;
import dbp.proyecto.song.infrastructure.SongRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class SongService {

    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SongService(SongRepository songRepository, ArtistRepository artistRepository, AlbumRepository albumRepository, PostRepository postRepository, ModelMapper modelMapper) {
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
        this.albumRepository = albumRepository;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    private SongResponseDTO getSongResponseDTO(Song song) {
        SongResponseDTO songResponseDTO = modelMapper.map(song, SongResponseDTO.class);
        if (song.getAlbum() != null) {
            songResponseDTO.setAlbumTitle(song.getAlbum().getTitle());
        }
        List<String> artistsNames = song.getArtists().stream()
                .map(Artist::getName)
                .collect(Collectors.toList());
        songResponseDTO.setArtistsNames(artistsNames);
        return songResponseDTO;
    }

    private List<SongInfoForArtistDTO> getSongInfoForArtistDTOS(Artist artist) {
        List<Song> songs = artist.getSongs();

        return songs.stream().map(song -> {
            SongInfoForArtistDTO songInfoForArtistDTO = modelMapper.map(song, SongInfoForArtistDTO.class);
            if(song.getAlbum() != null){
                songInfoForArtistDTO.setAlbumTitle(song.getAlbum().getTitle());
            }
            return songInfoForArtistDTO;
        }).collect(Collectors.toList());
    }

    public SongResponseDTO getSongById(Long id) {
        Song song = songRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return getSongResponseDTO(song);
    }

    public SongResponseDTO getSongByTitle(String title){
        Song song = songRepository.findByTitle(title);
        if(song == null){
            throw new ResourceNotFoundException("Song not found by that title");
        }
        return getSongResponseDTO(song);
    }

    public List<SongResponseDTO> getSongsByGenre(String genre) { //Done
        List<Song> songs = songRepository.findByGenre(genre);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that genre");
        }
        return songs.stream()
                .map(this::getSongResponseDTO)
                .collect(Collectors.toList());
    }

    public List<SongInfoForArtistDTO> getSongsByArtistId(Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found"));
        return getSongInfoForArtistDTOS(artist);
    }

    public List<SongInfoForArtistDTO> getSongsByArtistName(String artistName) {
        Artist artist = artistRepository.findByName(artistName);
        if(artist == null){
            throw new ResourceNotFoundException("Artist not found");
        }
        return getSongInfoForArtistDTOS(artist);
    }

    public List<SongInfoForAlbumDTO> getSongsByAlbumId(Long albumId) {
        List<Song> songs = songRepository.findByAlbumId(albumId);
        if (songs.isEmpty()) {
            throw new ResourceNotFoundException("Album not found");
        }
        return songs.stream().map(song -> {
            SongInfoForAlbumDTO songInfoForAlbumDTO = modelMapper.map(song, SongInfoForAlbumDTO.class);
            List<String> artistsNames = song.getArtists().stream()
                    .map(Artist::getName)
                    .collect(Collectors.toList());
            songInfoForAlbumDTO.setArtistsNames(artistsNames);
            return songInfoForAlbumDTO;
        }).collect(Collectors.toList());
    }

    public Page<SongResponseDTO> getAllSongs(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Song> songs = songRepository.findAll(pageable);
        return songs.map(this::getSongResponseDTO);
    }

    @Transactional
    public void createSongs(List<SongBodyDTO> songBodyDTOs) {
        List<Song> songs = new ArrayList<>();
        Random random = new Random();
        for (SongBodyDTO songBodyDTO : songBodyDTOs) {
            Song song = modelMapper.map(songBodyDTO, Song.class);
            int likes = 50000 + random.nextInt(450001);
            song.setLikes(likes);
            int timesPlayed = 50000 + random.nextInt(9550001);
            song.setTimesPlayed(timesPlayed);
            songRepository.save(song);
            List<Artist> artists = artistRepository.findAllById(songBodyDTO.getArtistsIds());
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

    public void updateCoverImage(String coverImage, Long id){
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
