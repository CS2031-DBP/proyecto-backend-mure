package dbp.proyecto.album.domain;

import dbp.proyecto.artist.domain.Artist;
import dbp.proyecto.song.domain.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AlbumTest {
    private Album album;

    @BeforeEach
    public void setUp() {
        album = new Album();
        album.setTitle("Album title");
        album.setDescription("Album description");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));
        Artist artist1 = new Artist();
        artist1.setName("Artist 1");
        Artist artist2 = new Artist();
        artist2.setName("Artist 2");

        Song song1 = new Song();
        song1.setTitle("Song 1");
        song1.setDuration("02:00");
        song1.setGenre("Pop");

        song1.setArtists(Collections.singletonList(artist1));

        Song song2 = new Song();
        song2.setTitle("Song 2");
        song2.setDuration("03:00");
        song2.setGenre("Rock");
        song2.setArtists(Arrays.asList(artist1, artist2));

        Song song3 = new Song();
        song3.setTitle("Song 3");
        song3.setDuration("01:30");
        song3.setGenre("Jazz");
        song3.setArtists(Collections.singletonList(artist1));

        album.setSongs(Arrays.asList(song1, song2, song3));
        album.setArtist(artist1);
        album.calculateSongsCount();
        album.calculateTotalDuration();
    }

    @Test
    public void testAlbumCreation() {
        assertNotNull(album);
        assertEquals("Artist 1", album.getArtist().getName());
        assertEquals(3, album.getSongs().size());
        assertEquals("Album title", album.getTitle());
        assertEquals("Album description", album.getDescription());
        assertEquals(LocalDate.parse("2021-01-01"), album.getReleaseDate());
    }

    @Test
    public void testAddSong() {
        Song song4 = new Song();
        song4.setTitle("Song 4");
        song4.setDuration("02:30");
        song4.setGenre("Pop");
        song4.setArtists(Collections.singletonList(album.getArtist()));
        List<Song> songs = new ArrayList<>(album.getSongs());
        songs.add(song4);
        album.setSongs(songs);
        assertEquals(4, album.getSongs().size());
    }

    @Test
    public void testRemoveSong(){
        List<Song> songs = new ArrayList<>(album.getSongs());
        songs.remove(0);
        album.setSongs(songs);
        assertEquals(2, album.getSongs().size());
    }

    @Test
    public void changeArtist() {
        Artist artist = new Artist();
        artist.setName("Artist 3");
        album.setArtist(artist);
        assertEquals("Artist 3", album.getArtist().getName());
    }

    @Test
    public void testCalculateTotalDuration() {
        assertEquals("00:06:30", album.getTotalDuration());
    }

    @Test
    public void testCalculateSongsCount() {
        assertEquals(3, album.getSongsCount());
    }

    @Test
    public void testAlbumNull(){
        Album album = new Album();
        assertNull(album.getArtist());
        assertTrue(album.getSongs().isEmpty());
        assertNull(album.getTitle());
        assertNull(album.getDescription());
        assertNull(album.getReleaseDate());
        assertNull(album.getTotalDuration());
        assertNull(album.getSongsCount());
    }
}