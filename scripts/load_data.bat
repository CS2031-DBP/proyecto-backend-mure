@echo off
set pgHost=localhost
set pgPort=5433
set pgUsername=postgres
set pgDatabase=database
set PGPASSWORD=postgres

set sqlCommand1=\COPY artist (name, name_normalized, birth_date, verified, description, image_url) FROM '../data/artists.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set sqlCommand2=\COPY song (title, title_normalized, genre, release_date, duration, cover_image, likes, times_played, album_id, link) FROM '../data/songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set sqlCommand3=\COPY album (title, title_normalized, description, release_date, total_duration, songs_count, cover_image, link, artist_id) FROM '../data/albums.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';
set sqlCommand4=\COPY artist_songs (artist_id, song_id) FROM '../data/artist_songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';

psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%sqlCommand1%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%sqlCommand3%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%sqlCommand2%"
psql -h %pgHost% -p %pgPort% -U %pgUsername% -d %pgDatabase% -c "%sqlCommand4%"
