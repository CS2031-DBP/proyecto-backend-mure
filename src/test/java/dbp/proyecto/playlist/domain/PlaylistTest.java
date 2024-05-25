package dbp.proyecto.playlist.domain;

import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.Role;
import dbp.proyecto.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PlaylistTest {
    private Playlist playlist;

    @BeforeEach
    public void setUp() {
        playlist = new Playlist();
        playlist.setName("Playlist 1");

        Song song1 = new Song();
        song1.setTitle("Song 1");
        song1.setDuration("02:00");
        song1.setGenre("Pop");

        Song song2 = new Song();
        song2.setTitle("Song 2");
        song2.setDuration("03:00");
        song2.setGenre("Rock");

        Song song3 = new Song();
        song3.setTitle("Song 3");
        song3.setDuration("01:30");
        song3.setGenre("Jazz");

        playlist.setSongs(Arrays.asList(song1, song2, song3));

        User user = new User();
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setBirthDate(LocalDate.parse("1990-01-01"));
        user.setRole(Role.USER);
        user.setOwnsPlaylists(Collections.singletonList(playlist));
        playlist.setUsers(Collections.singletonList(user));
    }

    @Test
    public void testPlaylistCreation() {
        assertNotNull(playlist);
        assertEquals("Playlist 1", playlist.getName());
        assertEquals(3, playlist.getSongs().size());
        assertNotNull(playlist.getUsers());
        assertEquals(1, playlist.getUsers().size());
    }

    @Test
    public void testAddSong() {
        Song song4 = new Song();
        song4.setTitle("Song 4");
        song4.setDuration("02:30");
        song4.setGenre("Pop");
        List<Song> songs = new ArrayList<>(playlist.getSongs());
        songs.add(song4);
        playlist.setSongs(songs);
        assertEquals(4, playlist.getSongs().size());
    }

    @Test
    public void testRemoveSong(){
        List<Song> songs = new ArrayList<>(playlist.getSongs());
        songs.remove(0);
        playlist.setSongs(songs);
        assertEquals(2, playlist.getSongs().size());
    }

    @Test
    public void testAddUser() {
        User user2 = new User();
        user2.setName("User 2");
        user2.setEmail("user2@example.com");
        user2.setBirthDate(LocalDate.parse("1991-01-01"));
        user2.setRole(Role.USER);
        user2.setOwnsPlaylists(Collections.singletonList(playlist));
        List<User> users = new ArrayList<>(playlist.getUsers());
        users.add(user2);
        playlist.setUsers(users);
        assertEquals(2, playlist.getUsers().size());
    }

    @Test
    public void testRemoveUser(){
        List<User> users = new ArrayList<>(playlist.getUsers());
        users.remove(0);
        playlist.setUsers(users);
        assertEquals(0, playlist.getUsers().size());
    }

    @Test
    public void testPlaylistNull(){
        Playlist playlist = new Playlist();
        assertNull(playlist.getName());
        assertTrue(playlist.getSongs().isEmpty());
        assertTrue(playlist.getUsers().isEmpty());
    }
}