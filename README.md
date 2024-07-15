# Mure 🎧
___

Mure es una aplicación web desarrollada por estudiantes de la Universidad de Ingeniería y Tecnología como proyecto para el curso de Desarrollo Basado en Plataformas.
El objetivo de este proyecto es desarrollar un sitio web completo usando Spring Boot como framework y aplicando lo aprendido en el curso.

<br>

## Miembros del Proyecto 🤝
___
| Nombre           | Email                                                            |
|------------------|------------------------------------------------------------------|
| Carlo Torres     | [carlo.torres@utec.edu.pe](mailto:carlo.torres@utec.edu.pe)      |
| Joaquin Salinas  | [joaquin.salinas@utec.edu.pe](mailto:joaquin.salinas@utec.edu.pe)|
| Santiago Cama    | [santiago.cama@utec.edu.pe](mailto:santiago.cama@utec.edu.pe)    |
| Guillermo Galvez | [jose.galvez.p@utec.edu.pe](mailto:jose.galvez.p@utec.edu.pe)    |

<br>

## Diagrama Entidad-Relación
___
![ER Mure Final](https://github.com/CS2031-DBP/proyecto-backend-mure/assets/103973127/2e3f8ead-cff0-4f8a-968e-c7238b516574)


Enlace diagrama creado con dbdiagram.io: [Diagrama ER - Mure](https://dbdiagram.io/d/Back-end-Mure-6652f2a3f84ecd1d22280266)

<br>

## Endpoints 🛣️

Album 🎵
| Método | Endpoint                    | Descripción                               |
|--------|-----------------------------|-------------------------------------------|
| GET    | /album/{id}                 | Obtener un álbum por su ID.               |
| GET    | /album/title                | Obtener un álbum por su título.           |
| GET    | /album/artist/{artistId}    | Obtener los álbumes de un artista por su ID. |
| GET    | /album/artistName           | Obtener los álbumes de un artista por su nombre. |
| GET    | /album/all                  | Obtener todos los álbumes.                |
| POST   | /album                      | Crear uno o más álbumes.                  |
| PATCH  | /album/{id}                 | Actualizar un álbum por su ID.            |
| DELETE | /album/{id}                 | Eliminar un álbum por su ID.              |

<br>

Artists 🎤
| Método | Endpoint                    | Descripción                                              |
|--------|-----------------------------|----------------------------------------------------------|
| GET    | /artist/{id}                | Obtener un artista por su ID.                            |
| GET    | /artist/verified            | Obtener todos los artistas verificados.                  |
| GET    | /artist/name                | Obtener un artista por su nombre.                        |
| GET    | /artist/songs/{songId}      | Obtener los artistas que participan en una canción por su ID. |
| GET    | /artist/songTitle           | Obtener los artistas que participan en una canción por su título. |
| GET    | /artist/all                 | Obtener todos los artistas.                              |
| POST   | /artist                     | Crear uno o más artistas.                                |
| PATCH  | /artist/{id}                | Actualizar la información de un artista por su ID.       |
| DELETE | /artist/{id}                | Eliminar un artista por su ID.                           |

<br>

Authentication 🔐
| Método | Endpoint        | Descripción                      |
|--------|-----------------|----------------------------------|
| POST   | /auth/login     | Iniciar sesión con un usuario existente. |
| POST   | /auth/signin    | Registrar un nuevo usuario.      |

<br>

Playlists 📋
| Método | Endpoint                              | Descripción                                |
|--------|---------------------------------------|--------------------------------------------|
| GET    | /playlist/{id}                        | Obtener una playlist por su ID.            |
| GET    | /playlist/name                        | Obtener una playlist por su nombre.        |
| GET    | /playlist/user/{id}                   | Obtener las playlists de un usuario por su ID. |
| GET    | /playlist/me                          | Obtener las playlists del usuario actual.  |
| GET    | /playlist/all                         | Obtener todas las playlists.               |
| POST   | /playlist                             | Crear una o más playlists.                 |
| PATCH  | /playlist/{id}/addSong/{songId}       | Añadir una canción a una playlist.         |
| PATCH  | /playlist/{id}/removeSong/{songId}    | Eliminar una canción de una playlist.      |
| DELETE | /playlist/{id}                        | Eliminar una playlist por su ID.           |

<br>

Posts 📝
| Método | Endpoint                    | Descripción                                        |
|--------|-----------------------------|----------------------------------------------------|
| GET    | /post/{id}                  | Obtener un post por su ID.                         |
| GET    | /post/user/{userId}         | Obtener los posts de un usuario por su ID.         |
| GET    | /post/me                    | Obtener los posts del usuario actual.              |
| GET    | /post/all                   | Obtener todos los posts.                           |
| GET    | /post/song/{songId}         | Obtener los posts relacionados con una canción por su ID. |
| GET    | /post/album/{albumId}       | Obtener los posts relacionados con un álbum por su ID. |
| POST   | /post                       | Crear uno o más posts.                             |
| PATCH  | /post/media/{id}            | Actualizar el contenido multimedia de un post.     |
| PATCH  | /post/content/{id}          | Actualizar el contenido de un post (canción y/o álbum relacionado). |
| PATCH  | /post/like/{id  }           | Actualizar el numero de likes de un post     |
| DELETE | /post/{id}                  | Eliminar un post por su ID.                        |

<br>

Songs 🎶
| Método | Endpoint                    | Descripción                                  |
|--------|-----------------------------|----------------------------------------------|
| GET    | /songs/{id}                 | Obtener una canción por su ID.               |
| GET    | /songs/title                | Obtener una canción por su título.           |
| GET    | /songs/genre                | Obtener canciones por género.                |
| GET    | /songs/artist/{artistId}    | Obtener las canciones de un artista por su ID. |
| GET    | /songs/artistName           | Obtener las canciones de un artista por su nombre. |
| GET    | /songs/album/{albumId}      | Obtener las canciones de un álbum por su ID. |
| GET    | /songs/all                  | Obtener todas las canciones.                 |
| POST   | /songs                      | Crear una o más canciones.                   |
| PATCH  | /songs/{id}/coverImage      | Actualizar la imagen de portada de una canción. |
| DELETE | /songs/{id}                 | Eliminar una canción por su ID.              |

<br>

Stories 📖
| Método | Endpoint                    | Descripción                                         |
|--------|-----------------------------|-----------------------------------------------------|
| GET    | /story/{id}                 | Obtener una historia por su ID.                     |
| GET    | /story/user/{userId}        | Obtener las historias de un usuario por su ID.      |
| GET    | /story/me                   | Obtener las historias del usuario actual.           |
| GET    | /story/all                  | Obtener todas las historias.                        |
| GET    | /story/song/{songId}        | Obtener las historias relacionadas con una canción por su ID. |
| POST   | /story                      | Crear una o más historias.                          |
| DELETE | /story/{id}                 | Eliminar una historia por su ID.                    |

<br>

User 👤
| Método | Endpoint                              | Descripción                                         |
|--------|---------------------------------------|-----------------------------------------------------|
| GET    | /user/me                              | Obtener información básica del usuario actual.      |
| GET    | /user/{id}                            | Obtener información básica de un usuario por su ID. |
| GET    | /user/all                             | Obtener información básica de todos los usuarios.   |
| GET    | /user/friends/{id}                    | Obtener los amigos de un usuario por su ID.         |
| GET    | /user/friends/me                      | Obtener los amigos del usuario actual.              |
| PATCH  | /user/update/me                       | Actualizar la información del usuario actual.       |
| PATCH  | /user/friends/add/{friendId}          | Añadir un amigo al usuario actual.                  |
| PATCH  | /user/friends/remove/{friendId}       | Eliminar un amigo del usuario actual.               |
| DELETE | /user/{id}                            | Eliminar un usuario por su ID.                      |
| GET    | /user/favoriteAlbums/{id}             | Obtener los albumes favoritos de un usuario por su ID. |
| GET    | /user/favoriteSongs/{id}              | Obtener las canciones favoritas de un usuario por su ID. |


<br>

<br>

[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-24ddc0f5d75046c5622901739e7c5dd533143b0c8e959d652212380cedb1ea36.svg)](https://classroom.github.com/a/XbBOibGW)
