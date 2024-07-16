package dbp.proyecto.song.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.artist.domain.Artist;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SongTest {
    private Song song;

    @BeforeEach
    public void setUp() {
        song = new Song();
        song.setTitle("Song 1");
        song.setDuration("02:00");
        song.setGenre("Pop");
        song.setReleaseDate(LocalDate.parse("2021-01-01"));
        song.setCoverImageUrl("cover.jpg");
        song.setLikes(10000);
        song.setTimesPlayed(250000);

        Artist artist1 = new Artist();
        artist1.setName("Artist 1");
        Artist artist2 = new Artist();
        artist2.setName("Artist 2");

        song.setArtists(Arrays.asList(artist1, artist2));

        Album album = new Album();
        album.setSongs(Collections.singletonList(song));
        album.setArtist(artist1);
        album.setTitle("Album title");
        album.setDescription("Album description");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));
        album.calculateSongsCount();
        album.calculateTotalDuration();

        song.setAlbum(album);
    }

    @Test
    public void testSongCreation() {
        assertNotNull(song);
        assertEquals("Song 1", song.getTitle());
        assertEquals("02:00", song.getDuration());
        assertEquals("Pop", song.getGenre());
        assertEquals(LocalDate.parse("2021-01-01"), song.getReleaseDate());
        assertEquals("cover.jpg", song.getCoverImageUrl());
        assertEquals(10000, song.getLikes());
        assertEquals(250000, song.getTimesPlayed());
        assertEquals(2, song.getArtists().size());
        assertNotNull(song.getAlbum());
    }

    @Test
    public void testAddArtist() {
        Artist artist3 = new Artist();
        artist3.setName("Artist 3");
        List<Artist> artists = new ArrayList<>(song.getArtists());
        artists.add(artist3);
        song.setArtists(artists);
        assertEquals(3, song.getArtists().size());
    }

    @Test
    public void testRemoveArtist() {
        List<Artist> artists = new ArrayList<>(song.getArtists());
        artists.remove(0);
        song.setArtists(artists);
        assertEquals(1, song.getArtists().size());
    }

    @Test
    public void changeAlbum() {
        Album album = new Album();
        album.setTitle("Album title 2");
        album.setDescription("Album description 2");
        album.setReleaseDate(LocalDate.parse("2021-01-02"));
        album.calculateSongsCount();
        album.calculateTotalDuration();
        song.setAlbum(album);
        assertEquals("Album title 2", song.getAlbum().getTitle());
        assertEquals("Album description 2", song.getAlbum().getDescription());
        assertEquals(LocalDate.parse("2021-01-02"), song.getAlbum().getReleaseDate());
    }

    @Test
    public void testGetDurationInSeconds() {
        assertEquals(120, song.getDurationInSeconds());
    }
}
