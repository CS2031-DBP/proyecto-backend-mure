package dbp.proyecto.song.domain;

import dbp.proyecto.exception.ResourceNotFoundException;
import dbp.proyecto.exception.UniqueResourceAlreadyExist;
import dbp.proyecto.song.dto.SongsDTO;
import dbp.proyecto.song.infrastructure.SongRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    private final SongRepository repository;
    private final ModelMapper modelMapper;

    @Autowired
    public SongService(SongRepository repository, ModelMapper modelMapper) {
        this.repository = repository;
        this.modelMapper = modelMapper;
    }

    public SongsDTO getSongsdtoById(Long id){ //Done
        Song song = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));
        return modelMapper.map(song, SongsDTO.class);
    }

    public SongsDTO getSongsDtoByTittle(String title){ //Done
        Song song = repository.findByTitle(title).orElseThrow(() -> new ResourceNotFoundException("Song not found by that artist"));
            return modelMapper.map(song, SongsDTO.class);
    }

    public List<SongsDTO> getSongByAnSpecificArtist(String artist) { //Done
        List<Song> songs = repository.findByArtists(artist);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that artist");
        }
        return songs.stream()
                .map(song -> modelMapper.map(song, SongsDTO.class))
                .collect(Collectors.toList());
    }

    public List<SongsDTO> getSongByGenre(String genre){ //Done
        List<Song> songs = repository.findByGenre(genre);

        if(songs.isEmpty()){
            throw new ResourceNotFoundException("Song not found by that genre");
        }
        return songs.stream().map(song -> modelMapper.map(song, SongsDTO.class))
                .collect(Collectors.toList());
    }

    public void postSong(Song song){ //Done
        if(repository.findByTitle(song.getTitle()).isPresent()){
            throw new UniqueResourceAlreadyExist("Song already exists");
        }
        repository.save(song);
    }

    public void updateCoverImage(String coverImage, Long id){ //Done
        Song song = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Song not found"));

        if(song.getCoverImage() != null && song.getCoverImage().equals(coverImage)){
            song.setCoverImage(null);
        }else{
            throw new ResourceNotFoundException("Cover image not found");
        }
    }

    public void deleteSong(Long id){ //Done
        if(repository.findById(id).isPresent()){
            repository.deleteById(id);
        }else{
            throw new ResourceNotFoundException("Song not found or already deleted");
        }
    }
}
