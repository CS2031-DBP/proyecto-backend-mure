$pgHost = "localhost"
$pgPort = "5433"
$pgUsername = "postgres"
$pgDatabase = "database"
$env:PGPASSWORD = "postgres"

$sqlCommand1 = "\COPY artist (id, name, name_normalized, birth_date, verified, description, image_url) FROM '../data/artists.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';"
$sqlCommand2 = "\COPY song (id, title, title_normalized, genre, release_date, duration, cover_image, likes, times_played) FROM '../data/songs.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';"
$sqlCommand3 = "\COPY album (id, title, title_normalized, description, release_date, total_duration, songs_count, cover_image, link, artist_id) FROM '../data/albums.csv' DELIMITER ',' CSV HEADER ENCODING 'utf-8';"

& psql -h localhost -p 5433 -U postgres -d database -c $sqlCommand1
& psql -h localhost -p 5433 -U postgres -d database -c $sqlCommand2
& psql -h localhost -p 5433 -U postgres -d database -c $sqlCommand3
