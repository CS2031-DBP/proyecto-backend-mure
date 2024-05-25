package dbp.proyecto.artist.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class ArtistTest {
    private Artist artist;

    @BeforeEach
    public void setUp() {
        artist = new Artist();
        artist.setName("Artist 1");
        artist.setBirthDate(LocalDate.parse("1990-01-01"));
        artist.setVerified(true);
        artist.setDescription("Artist description");

        Song song1 = new Song();
        song1.setTitle("Song 1");
        song1.setDuration("02:00");
        song1.setGenre("Pop");
        song1.setArtists(Collections.singletonList(artist));

        Song song2 = new Song();
        song2.setTitle("Song 2");
        song2.setDuration("03:00");
        song2.setGenre("Rock");
        song2.setArtists(Collections.singletonList(artist));

        Song song3 = new Song();
        song3.setTitle("Song 3");
        song3.setDuration("01:30");
        song3.setGenre("Jazz");
        song3.setArtists(Collections.singletonList(artist));

        artist.setSongs(Arrays.asList(song1, song2, song3));

        Album album = new Album();
        album.setSongs(Arrays.asList(song1, song2, song3));
        album.setArtist(artist);
        album.setTitle("Album title");
        album.setDescription("Album description");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));
        album.calculateSongsCount();
        album.calculateTotalDuration();

        artist.setAlbums(Collections.singletonList(album));
    }

    @Test
    public void testArtistCreation() {
        assertNotNull(artist);
        assertEquals("Artist 1", artist.getName());
        assertEquals(LocalDate.parse("1990-01-01"), artist.getBirthDate());
        assertTrue(artist.getVerified());
        assertEquals("Artist description", artist.getDescription());
        assertEquals(3, artist.getSongs().size());
        assertEquals(1, artist.getAlbums().size());
    }

    @Test
    public void testAddSong() {
        Song song4 = new Song();
        song4.setTitle("Song 4");
        song4.setDuration("02:30");
        song4.setGenre("Pop");
        song4.setArtists(Collections.singletonList(artist));
        List<Song> songs = new ArrayList<>(artist.getSongs());
        songs.add(song4);
        artist.setSongs(songs);
        assertEquals(4, artist.getSongs().size());
    }

    @Test
    public void testAddAlbum() {
        Album album2 = new Album();
        album2.setSongs(artist.getSongs());
        album2.setArtist(artist);
        album2.setTitle("Album title 2");
        album2.setDescription("Album description 2");
        album2.setReleaseDate(LocalDate.parse("2021-01-01"));
        album2.calculateSongsCount();
        album2.calculateTotalDuration();
        List<Album> albums = new ArrayList<>(artist.getAlbums());
        albums.add(album2);
        artist.setAlbums(albums);
        assertEquals(2, artist.getAlbums().size());
    }

    @Test
    public void testRemoveSong(){
        List<Song> songs = new ArrayList<>(artist.getSongs());
        songs.remove(0);
        artist.setSongs(songs);
        assertEquals(2, artist.getSongs().size());
    }

    @Test
    public void testRemoveAlbum(){
        List<Album> albums = new ArrayList<>(artist.getAlbums());
        albums.remove(0);
        artist.setAlbums(albums);
        assertEquals(0, artist.getAlbums().size());
    }

    @Test
    public void testArtistNull(){
        Artist artist = new Artist();
        assertNull(artist.getName());
        assertNull(artist.getBirthDate());
        assertNull(artist.getVerified());
        assertNull(artist.getDescription());
        assertTrue(artist.getSongs().isEmpty());
        assertTrue(artist.getAlbums().isEmpty());
    }
}