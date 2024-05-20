package dbp.proyecto.song.domain;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.artist.infrastructure.ArtistRepository;
import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.song.dto.SongBodyDTO;
import dbp.proyecto.song.dto.SongsResponseDTO;
import dbp.proyecto.song.infrastructure.SongRepository;
import dbp.proyecto.tablasIntermedias.artistSongs.ArtistSongs;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository repository;

    private final ArtistRepository artistRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public SongService(SongRepository repository, ArtistRepository artistRepository, ModelMapper modelMapper) {
        this.repository = repository;
        this.artistRepository = artistRepository;
        this.modelMapper = modelMapper;
    }

    public SongsResponseDTO getSongsdtoById(Long id){ //Done
        Song song = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return modelMapper.map(song, SongsResponseDTO.class);
    }

    public List<SongsResponseDTO> getSongsDtoByTittle(String title){ //Done
        List<Song> songs = repository.findByTitle(title);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that title");
        }

        return songs.stream()
                .map(song -> modelMapper.map(song, SongsResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<SongsResponseDTO> getSongByArtists(List<ArtistSongs> artists) { //Done
        List<Song> songs = repository.findByArtists(artists);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that artist");
        }
        return songs.stream()
                .map(song -> modelMapper.map(song, SongsResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<SongsResponseDTO> getSongByGenre(String genre){ //Done
        List<Song> songs = repository.findByGenre(genre);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that genre");
        }
        return songs.stream().map(song -> modelMapper.map(song, SongsResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<String> postSongs(List<SongBodyDTO> songs) {
        List<String> savedSongUrls = new ArrayList<>();

        for (SongBodyDTO song : songs) {
            Song newSong = new Song();
            newSong.setTitle(song.getTitle());
            newSong.setReleaseDate(song.getReleaseDate());
            newSong.setGenre(song.getGenre());
            newSong.setDuration(song.getDuration());

            List<Long> artists = song.getArtistsIds();
            if (artists.isEmpty()) {
                throw new ResourceNotFoundException("No artists found for song: " + song.getTitle());
            }
            for (Long artistId : artists) {
                Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new ResourceNotFoundException("Artist not found for song: " + song.getTitle()));
                ArtistSongs artistSongs = new ArtistSongs();
                artistSongs.setArtist(artist);
                artistSongs.setSong(newSong);
                newSong.getArtists().add(artistSongs);
            }

            Song savedSong = repository.save(newSong);
            savedSongUrls.add("/song/" + savedSong.getId());
        }

        return savedSongUrls;
    }


    public void updateCoverImage(String coverImage, Long id){
        Song song = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if(song.getCoverImage() != null && song.getCoverImage().equals(coverImage)){
            song.setCoverImage(null);
        }else{
            throw new ResourceNotFoundException("Cover image not found");
        }
    }

    public void deleteSong(Long id){ //Done
        Song song = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        repository.delete(song);
    }
}
