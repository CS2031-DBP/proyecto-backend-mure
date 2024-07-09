from scripts.data_setup.data.artists_dict import artists_dict
from scripts.data_setup.data_generation.artist_song_album import (
    generate_artists_songs_albums,
)
from scripts.data_setup.data_generation.playlist import generate_playlist_data
from scripts.data_setup.data_generation.post import generate_post_data
from scripts.data_setup.data_generation.user import generate_user_data
from scripts.data_setup.data_generation.user_albums import generate_user_albums_data
from scripts.data_setup.data_generation.user_friends import generate_user_friends_data
from scripts.data_setup.data_generation.user_song import generate_user_song_data


artists_keys, albums_keys, song_keys = generate_artists_songs_albums(artists_dict)
user_keys = generate_user_data(1000)
generate_post_data(len(user_keys) * 5, user_keys, song_keys, albums_keys)
generate_user_albums_data(len(user_keys) * 3, user_keys, albums_keys)
generate_user_friends_data(len(user_keys) * 50, user_keys)
generate_user_song_data(len(user_keys) * 100, user_keys, song_keys)
generate_playlist_data(len(user_keys) * 10, user_keys, song_keys)
