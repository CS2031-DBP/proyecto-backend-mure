package dbp.proyecto.user.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {
    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("User 1");
        user.setEmail("user1@example.com");
        user.setPassword("password1");
        user.setBirthDate(LocalDate.parse("1990-01-01"));
        user.setCreatedAt(LocalDateTime.now());
        user.setRole(Role.USER);
        user.setProfileImage("profile1.jpg");

        User friend1 = new User();
        friend1.setName("Friend 1");
        friend1.setEmail("friend1@example.com");
        friend1.setPassword("password2");
        friend1.setBirthDate(LocalDate.parse("1991-01-01"));
        friend1.setCreatedAt(LocalDateTime.now());
        friend1.setRole(Role.USER);
        friend1.setProfileImage("profile2.jpg");

        User friend2 = new User();
        friend2.setName("Friend 2");
        friend2.setEmail("friend2@example.com");
        friend2.setPassword("password3");
        friend2.setBirthDate(LocalDate.parse("1992-01-01"));
        friend2.setCreatedAt(LocalDateTime.now());
        friend2.setRole(Role.USER);
        friend2.setProfileImage("profile3.jpg");

        user.setFriends(List.of(friend1, friend2));
    }

    @Test
    public void testUserCreation() {
        assertNotNull(user);
        assertEquals("User 1", user.getName());
        assertEquals("user1@example.com", user.getEmail());
        assertEquals("password1", user.getPassword());
        assertEquals(LocalDate.parse("1990-01-01"), user.getBirthDate());
        assertEquals(LocalDateTime.now().getDayOfWeek(), user.getCreatedAt().getDayOfWeek());
        assertEquals(LocalDateTime.now().getYear(), user.getCreatedAt().getYear());
        assertEquals(LocalDateTime.now().getMonth(), user.getCreatedAt().getMonth());
        assertEquals(Role.USER, user.getRole());
        assertEquals("profile1.jpg", user.getProfileImage());
        assertEquals(2, user.getFriends().size());
    }

    @Test
    public void testAddFriend() {
        User friend3 = new User();
        friend3.setName("Friend 3");
        friend3.setEmail("friend3@example.com");
        friend3.setPassword("password4");
        friend3.setBirthDate(LocalDate.parse("1993-01-01"));
        friend3.setCreatedAt(LocalDateTime.now());
        friend3.setRole(Role.USER);
        friend3.setProfileImage("profile4.jpg");
        List<User> friends = new ArrayList<>(user.getFriends());
        friends.add(friend3);
        user.setFriends(friends);
        assertEquals(3, user.getFriends().size());
    }

    @Test
    public void testRemoveFriend(){
        List<User> friends = new ArrayList<>(user.getFriends());
        friends.remove(0);
        user.setFriends(friends);
        assertEquals(1, user.getFriends().size());
    }

    @Test
    public void testUserNull(){
        User user = new User();
        assertNull(user.getName());
        assertNull(user.getEmail());
        assertNull(user.getPassword());
        assertNull(user.getBirthDate());
        assertNull(user.getCreatedAt());
        assertNull(user.getRole());
        assertEquals("https://www.pngall.com/wp-content/uploads/5/User-Profile-PNG-Free-Download.png", user.getProfileImage());
        assertTrue(user.getFriends().isEmpty());
    }
}