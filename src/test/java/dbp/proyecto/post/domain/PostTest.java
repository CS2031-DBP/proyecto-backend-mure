package dbp.proyecto.post.domain;

import dbp.proyecto.album.domain.Album;
import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.Role;
import dbp.proyecto.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class PostTest {
    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post();
        post.setDescription("This is a post description");
        post.setLikes(500);
        post.setImageUrl("image.jpg");
        post.setAudioUrl("audio.mp3");
        post.setCreatedAt(LocalDateTime.now());

        User user = new User();
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setBirthDate(LocalDate.parse("1990-01-01"));
        user.setRole(Role.USER);
        user.setPosts(Collections.singletonList(post));

        post.setUser(user);
    }

    @Test
    public void testPostCreation() {
        assertNotNull(post);
        assertEquals("This is a post description", post.getDescription());
        assertEquals(500, post.getLikes());
        assertEquals("image.jpg", post.getImageUrl());
        assertEquals("audio.mp3", post.getAudioUrl());
        assertEquals(LocalDateTime.now().getDayOfWeek(), post.getCreatedAt().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), post.getCreatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), post.getCreatedAt().getMonth());
        assertNotNull(post.getUser());
    }

    @Test
    public void testAddSong() {
        Song song = new Song();
        song.setTitle("Song 1");
        song.setDuration("02:00");
        song.setGenre("Pop");
        post.setSong(song);
        assertNotNull(post.getSong());
    }

    @Test
    public void testAddAlbum() {
        Album album = new Album();
        album.setTitle("Album 1");
        album.setDescription("This is album 1");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));
        post.setAlbum(album);
        assertNotNull(post.getAlbum());
    }

    @Test
    public void testAddSongAndAlbum() {
        Song song = new Song();
        song.setTitle("Song 1");
        song.setDuration("02:00");
        song.setGenre("Pop");
        post.setSong(song);

        Album album = new Album();
        album.setTitle("Album 1");
        album.setDescription("This is album 1");
        album.setReleaseDate(LocalDate.parse("2021-01-01"));
        post.setAlbum(album);

        assertNotNull(post.getSong());
        assertNotNull(post.getAlbum());
    }

    @Test
    public void testPostNull(){
        Post post = new Post();
        assertNull(post.getDescription());
        assertNull(post.getCreatedAt());
        assertNull(post.getImageUrl());
        assertNull(post.getAudioUrl());
        assertNull(post.getUser());
        assertNull(post.getSong());
        assertNull(post.getAlbum());
    }
}