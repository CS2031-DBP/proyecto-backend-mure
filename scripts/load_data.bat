@echo off
set pgHost=localhost
set pgPort=5433
set pgUsername=postgres
set pgDatabase=database
set PGPASSWORD=postgres

set artistSqlCommand=\COPY artist (name, name_normalized, birth_date, verified, description, image_url) FROM '../assets/data/artists.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set albumSqlCommand=\COPY album (title, title_normalized, description, release_date, total_duration, songs_count, cover_image_url, spotify_url, artist_id) FROM '../assets/data/albums.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set songSqlCommand=\COPY song (title, title_normalized, genre, release_date, duration, cover_image_url, likes, times_played, album_id, spotify_url, spotify_preview_url) FROM '../assets/data/songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set artistSongsSqlCommand=\COPY artist_songs (artist_id, song_id) FROM '../assets/data/artist_songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set userSqlCommand=\COPY users (role, name, nickname, nickname_normalized, email, password, birth_date, created_at, profile_image_url) FROM '../assets/data/users.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set userSongsSqlCommand=\COPY user_song (user_id, song_id) FROM '../assets/data/user_songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set userFriendsSqlCommand=\COPY users_friends (user_id, friends_id) FROM '../assets/data/user_friends.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set userAlbumsSqlCommand=\COPY user_albums (album_id, user_id) FROM '../assets/data/user_albums.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set playlistSqlCommand=\COPY playlist (user_id, name) FROM '../assets/data/playlists.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set playlistSongsSqlCommand=\COPY playlist_songs (playlist_id, song_id) FROM '../assets/data/playlist_songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set postSqlCommand=\COPY post (likes, created_at, user_id, description, audio_url, image_url, song_id, album_id) FROM '../assets/data/posts.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';

psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%artistSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%albumSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%songSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%artistSongsSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%userSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%userSongsSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%userFriendsSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%userAlbumsSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%playlistSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%playlistSongsSqlCommand%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%postSqlCommand%"
