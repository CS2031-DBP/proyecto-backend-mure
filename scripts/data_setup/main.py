from itertools import islice

from data_setup.data.artists_dict import artists_dict
from data_setup.data_generation.artist_song_album import generate_artists_songs_albums
from data_setup.data_generation.playlist import generate_playlist_data
from data_setup.data_generation.post import generate_post_data
from data_setup.data_generation.user import generate_user_data
from data_setup.data_generation.user_albums import generate_user_albums_data
from data_setup.data_generation.user_friends import generate_user_friends_data
from data_setup.data_generation.user_song import generate_user_song_data

sliced_artists = islice(artists_dict.items(), len(artists_dict.items()))
sliced_artists_dict = dict(sliced_artists)
artists_keys, albums_keys, song_keys = generate_artists_songs_albums(
    sliced_artists_dict
)
user_keys = generate_user_data(500)
generate_post_data(len(user_keys) * 25, user_keys, song_keys, albums_keys)
generate_user_albums_data(len(user_keys) * 3, user_keys, albums_keys)
generate_user_friends_data(len(user_keys) * 20, user_keys)
generate_user_song_data(len(user_keys) * 15, user_keys, song_keys)
generate_playlist_data(len(user_keys) * 20, user_keys, song_keys)
