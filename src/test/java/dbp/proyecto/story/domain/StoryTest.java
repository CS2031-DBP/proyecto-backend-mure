package dbp.proyecto.story.domain;

import dbp.proyecto.song.domain.Song;
import dbp.proyecto.user.domain.Role;
import dbp.proyecto.user.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class StoryTest {
    private Story story;

    @BeforeEach
    public void setUp() {
        story = new Story();
        story.setCreatedAt(LocalDateTime.now());
        story.setExpiresAt(LocalDateTime.now().plusHours(24));
        story.setVideoUrl("video.mp4");
        story.setText("This is a story text");
        story.setLikes(200);

        User user = new User();
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setBirthDate(LocalDate.parse("1990-01-01"));
        user.setRole(Role.USER);
        user.setStories(Collections.singletonList(story));

        story.setUser(user);
    }

    @Test
    public void testStoryCreation() {
        assertNotNull(story);
        assertEquals("This is a story text", story.getText());
        assertEquals(200, story.getLikes());
        assertEquals("video.mp4", story.getVideoUrl());
        assertEquals(LocalDateTime.now().getDayOfWeek(), story.getCreatedAt().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), story.getCreatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), story.getCreatedAt().getMonth());
        assertNotNull(story.getUser());
    }

    @Test
    public void testAddSong() {
        Song song = new Song();
        song.setTitle("Song 1");
        song.setDuration("02:00");
        song.setGenre("Pop");
        story.setSong(song);
        assertNotNull(story.getSong());
    }

    @Test
    public void testStoryNull(){
        Story story = new Story();
        assertNull(story.getText());
        assertNull(story.getLikes());
        assertNull(story.getCreatedAt());
        assertNull(story.getExpiresAt());
        assertNull(story.getVideoUrl());
        assertNull(story.getUser());
        assertNull(story.getSong());
    }
}