package dbp.proyecto.song.infrastructure;

import dbp.proyecto.AbstractContainerBaseTest;
import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.domain.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SongRepositoryTest extends AbstractContainerBaseTest {

    private final SongRepository songRepository;
    private final TestEntityManager entityManager;

    @Autowired
    public SongRepositoryTest(SongRepository songRepository, TestEntityManager entityManager) {
        this.songRepository = songRepository;
        this.entityManager = entityManager;
    }

    private Song song;

    @BeforeEach
    public void setUp() {
        Artist artist = new Artist();
        artist.setName("Artist 1");
        artist.setBirthDate(LocalDate.parse("1990-01-01"));
        artist.setVerified(true);
        artist.setDescription("Artist description");

        Album album = new Album();
        album.setTitle("Album 1");
        album.setDescription("This is album 1");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));

        song = new Song();
        song.setTitle("Song 1");
        song.setDuration("02:00");
        song.setGenre("Pop");
        song.setReleaseDate(LocalDate.parse("2021-01-01"));
        song.setLikes(20000);
        song.setTimesPlayed(500000);
        song.setArtists(List.of(artist));
        song.setAlbum(album);

        entityManager.persist(artist);
        entityManager.persist(album);
        entityManager.persist(song);
        entityManager.flush();
    }

    @Test
    public void testCreateSong() {
        Song newSong = new Song();
        newSong.setTitle("Song 2");
        newSong.setDuration("03:00");
        newSong.setGenre("Rock");
        newSong.setReleaseDate(LocalDate.parse("2021-01-01"));
        newSong.setLikes(10000);
        newSong.setTimesPlayed(200000);
        newSong.setArtists(List.of(song.getArtists().get(0)));
        newSong.setAlbum(song.getAlbum());

        Song savedSong = songRepository.save(newSong);
        assertNotNull(savedSong);
        assertEquals(newSong.getId(), savedSong.getId());
    }

    @Test
    public void testFindById() {
        Song foundSong = songRepository.findById(song.getId()).orElse(null);
        assertNotNull(foundSong);
        assertEquals(song.getId(), foundSong.getId());
    }

    @Test
    public void testDeleteById() {
        songRepository.deleteById(song.getId());
        Song foundSong = songRepository.findById(song.getId()).orElse(null);
        assertNull(foundSong);
    }

    @Test
    public void testFindByTitle() {
        Song foundSong = songRepository.findByTitle(song.getTitle());
        assertNotNull(foundSong);
        assertEquals(song.getTitle(), foundSong.getTitle());
    }

    @Test
    public void testFindByGenre() {
        Page<Song> songs = songRepository.findByGenreContaining(song.getGenre(), PageRequest.of(5, 5));
        assertFalse(songs.isEmpty());
        assertTrue(songs.stream().anyMatch(s -> s.getTitle().equals(song.getTitle())));
    }

    @Test
    public void testFindByAlbumId() {
        List<Song> songs = songRepository.findByAlbumId(song.getAlbum().getId());
        assertFalse(songs.isEmpty());
        assertTrue(songs.stream().anyMatch(s -> s.getTitle().equals(song.getTitle())));
    }
}
