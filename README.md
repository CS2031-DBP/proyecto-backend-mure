# Mure: Share Your Music Taste 🎧 🎶
___

**Mure**, the winner of the *Berners Lee Award 2024-1* for the UTEC course *Platform-Based Development* (CS2031), is a platform designed for users to share their music preferences with friends and followers. With Mure, you can create and share posts, playlists, and explore music content 🎙️

The project is developed using **Java and Spring Boot 🌱** for the backend, while the frontend is built with **React ⚛️**, and the mobile application is developed with **React Native 📱**


## Project Members 🤝
___
| Name              | Email                                                               |
|-------------------|---------------------------------------------------------------------|
| Joaquin Salinas   | [joaquin.salinas@utec.edu.pe](mailto:joaquin.salinas@utec.edu.pe)   |
| Guillermo Galvez  | [jose.galvez.p@utec.edu.pe](mailto:jose.galvez.p@utec.edu.pe)       |
| Alejandro Escobar | [alejandro.escobar@utec.edu.pe](mailto:alejandro.escobar@utec.edu.pe)|

## Prerequisites 🔧
___
Before setting up the project, ensure you have the following installed on your machine:

- **Java**: Recommended version 17 or higher
- **PostgreSQL**: Recommended latest version 16
- **Docker**: Latest version
- **IntelliJ IDEA**: Latest version

## Getting Started 🚀
___

To set up the project on your local machine, follow these steps:

1. **Clone the Repository**

   Open your terminal and clone the repository using the following command:

   ```sh
   git clone https://github.com/CS2031-DBP/proyecto-backend-mure
   ```

2. **Navigate to the Project Directory**

   Change to the project directory:

   ```sh
   cd proyecto-backend-mure
   ```

3. **Open the Project in Your Preferred IDE**

   Preferably use IntelliJ IDEA for this project. Launch IntelliJ IDEA, click on "Open", and select the project directory you just cloned.

4. **Run the Application**

   Run the `MureApplication` class to start the application. IntelliJ will automatically handle the dependencies as specified in the `pom.xml` file since the project uses Maven.

5. **Set Up the Database**

   Ensure you have Docker installed on your machine. Then, run the following command to start the PostgreSQL database using Docker:

   ```sh
   docker-compose up
   ```

   Here is the Docker configuration for reference:

   ```yaml
   services:
     db:
       image: postgres:latest
       container_name: proyecto-backend-mure-dev
       restart: always
       environment:
         POSTGRES_PASSWORD: postgres
         POSTGRES_DB: database
       ports:
         - "5433:5432"
   ```

6. **Populate the Database**

   After running the application, execute the `.bat` script located in the `scripts` folder to populate the database.

Following these steps will set up the Mure project on your local machine, allowing you to explore and develop further.

## Entity-Relationship Diagram
___
![ER Diagram](https://github.com/user-attachments/assets/38d922c3-e2d6-4046-ac6e-b3cc7f186863)

## Endpoints 🛣️

**Album 🎵**

| Method | Endpoint                     | Description                                            |
|--------|------------------------------|--------------------------------------------------------|
| GET    | /album/{id}                  | Get an album by its ID.                                |
| GET    | /album/artist/{artistId}     | Get albums by artist ID.                               |
| GET    | /album/artistName            | Get albums by artist name.                             |
| GET    | /album/all                   | Get all albums.                                        |
| GET    | /album/title                 | Get an album by its title.                             |
| GET    | /album/liked/{albumId}/{userId} | Check if an album is liked by a user.                   |
| POST   | /album                       | Create one or more albums.                             |
| PATCH  | /album/{id}                  | Update an album by its ID.                             |
| PATCH  | /album/dislike/{id}          | Dislike an album.                                      |
| PATCH  | /album/like/{id}             | Like an album.                                         |
| DELETE | /album/{id}                  | Delete an album by its ID.                             |


**Artists 🎤**

| Method | Endpoint                    | Description                                              |
|--------|-----------------------------|----------------------------------------------------------|
| GET    | /artist/{id}                | Get an artist by their ID.                               |
| GET    | /artist/all                 | Get all artists.                                         |
| GET    | /artist/name                | Get an artist by their name.                             |
| GET    | /artist/verified            | Get all verified artists.                                |
| GET    | /artist/songTitle           | Get artists by song title.                               |
| GET    | /artist/songs/{songId}      | Get artists by song ID.                                  |
| POST   | /artist                     | Create one or more artists.                              |
| PATCH  | /artist/{id}                | Update an artist by their ID.                            |
| DELETE | /artist/{id}                | Delete an artist by their ID.                            |


**Authentication 🔐**

| Method | Endpoint         | Description                                            |
|--------|------------------|--------------------------------------------------------|
| POST   | /auth/login      | Log in with an existing user.                          |
| POST   | /auth/signin     | Register a new user.                                   |
| POST   | /auth/verify-password | Verify a user's password.                            |
| POST   | /auth/google     | Validate Google authentication token.                  |


**Comments 💬**

| Method | Endpoint                     | Description                                           |
|--------|------------------------------|-------------------------------------------------------|
| GET    | /comments/post/{postId}      | Get comments by post ID with pagination.              |
| POST   | /comments                    | Create a new comment.                                 |
| DELETE | /comments/{commentId}        | Delete a comment by its ID.                           |


**Playlists 📋**

| Method | Endpoint                              | Description                                | Data Type   |
|--------|---------------------------------------|--------------------------------------------|-------------|
| GET    | /playlist/{id}                        | Get a playlist by its ID.                  |             |
| GET    | /playlist/name                        | Get a playlist by its name.                |             |
| GET    | /playlist/user/{id}                   | Get playlists by user ID.                  |             |
| GET    | /playlist/me                          | Get current user's playlists.              |             |
| GET    | /playlist/all                         | Get all playlists.                         |             |
| POST   | /playlist                             | Create one or more playlists.              |             |
| POST   | /playlist/image                       | Create a playlist with an image.           | Multipart   |
| PATCH  | /playlist/{id}/addSong/{songId}       | Add a song to a playlist.                  |             |
| PATCH  | /playlist/{id}/removeSong/{songId}    | Remove a song from a playlist.             |             |
| PATCH  | /playlist                             | Update playlist name and image.            | Multipart   |
| DELETE | /playlist/{id}                        | Delete a playlist by its ID.               |             |


**Posts 📝**

| Method | Endpoint                    | Description                                            | Data Type   |
|--------|-----------------------------|--------------------------------------------------------|-------------|
| GET    | /post/{id}                  | Get a post by its ID.                                  |             |
| GET    | /post/user/{userId}         | Get posts by user ID.                                  |             |
| GET    | /post/me                    | Get current user's posts.                              |             |
| GET    | /post/all                   | Get all posts.                                         |             |
| GET    | /post/song/{songId}         | Get posts related to a song by its ID.                 |             |
| GET    | /post/album/{albumId}       | Get posts related to an album by its ID.               |             |
| POST   | /post                       | Create one or more posts.                              | Multipart   |
| POST   | /post/many                  | Create multiple posts.                                 |             |
| PATCH  | /post/media/{id}            | Update the multimedia content of a post.               |             |
| PATCH  | /post/content/{id}          | Update the content of a post (related song and/or album).|            |
| PATCH  | /post/like/{id}             | Update the number of likes of a post.                  |             |
| PATCH  | /post/dislike/{id}          | Update the number of dislikes of a post.               |             |
| DELETE | /post/{id}                  | Delete a post by its ID.                               |             |


**Songs 🎶**

| Method | Endpoint                    | Description                                            |
|--------|-----------------------------|--------------------------------------------------------|
| GET    | /songs/{id}                 | Get a song by its ID.                                  |
| GET    | /songs/title                | Get a song by its title.                               |
| GET    | /songs/genre                | Get songs by genre.                                    |
| GET    | /songs/artist/{artistId}    | Get songs by artist ID.                                |
| GET    | /songs/artistName           | Get songs by artist name.                              |
| GET    | /songs/album/{albumId}      | Get songs by album ID.                                 |
| GET    | /songs/all                  | Get all songs.                                         |
| POST   | /songs                      | Create one or more songs.                              |
| PATCH  | /songs/{id}/coverImage      | Update the cover image of a song.                      |
| DELETE | /songs/{id}                 | Delete a song by its ID.                               |



**Stories 📖**

| Method | Endpoint             | Description                                            |
|--------|----------------------|--------------------------------------------------------|
| GET    | /story/{id}          | Get a story by its ID.                                 |
| GET    | /story/user/{userId} | Get stories by user ID.                                |
| GET    | /story/me            | Get current user's stories.                            |
| GET    | /story/all           | Get all stories.                                       |
| GET    | /story/song/{songId} | Get stories related to a song by its ID.               |
| POST   | /story               | Create one or more stories.                            |
| DELETE | /story/{id}          | Delete a story by its ID.                              |


**Users 👤**

| Method | Endpoint                              | Description                                            | Data Type   |
|--------|---------------------------------------|--------------------------------------------------------|-------------|
| GET    | /user/me                              | Get basic information of the current user.             |             |
| GET    | /user/{id}                            | Get basic information of a user by their ID.           |             |
| GET    | /user/all                             | Get basic information of all users.                    |             |
| GET    | /user/friends/me                      | Get friends of the current user.                       |             |
| GET    | /user/friends/{id}                    | Get friends of a user by their ID.                     |             |
| GET    | /user/favoriteAlbums/{id}             | Get favorite albums of a user by their ID.             |             |
| GET    | /user/favoriteSongs/{id}              | Get favorite songs of a user by their ID.              |             |
| GET    | /user/me/friends/{id}                 | Check if a user is a friend of the current user.       |             |
| POST   | /user/expo-token/{userId}             | Register the Expo token for a user.                    |             |
| PATCH  | /user/update/me                       | Update the information of the current user.            | Multipart   |
| PATCH  | /user/friends/add/{friendId}          | Add a friend to the current user.                      |             |
| PATCH  | /user/friends/remove/{friendId}       | Remove a friend from the current user.                 |             |
| DELETE | /user/{id}                            | Delete a user by their ID.                             |             |

## Acknowledgments 🫶
___
We would like to thank everyone who supported the project by testing it and providing valuable feedback. Special thanks to our professor, Jorge Rios, whose guidance and encouragement were crucial to the successful development of this project 🗣️ 🙌

## License 📄

This project is licensed under the [GNU General Public License v3.0](http://www.gnu.org/licenses/gpl-3.0.html).
