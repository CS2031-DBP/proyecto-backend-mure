from itertools import islice
import pandas as pd
from faker import Faker

from scripts.data_generation.data.artists_dict import artist_dict
from scripts.data_generation.spotify_methods import *
from scripts.data_generation.utils.normalize_text import normalize_text

fake = Faker()

artists_csv_data = []
albums_csv_data = []
songs_csv_data = []
artist_songs = []
album_counter = 0
song_counter = 0

for artist_id, artist_name in islice(artist_dict.items(), 10):
    print(artist_name)
    artist_spotify_id = get_artist_id(artist_name)

    if not artist_spotify_id:
        continue

    image_url = get_artist_details(artist_spotify_id)
    birth_date = fake.date_of_birth(minimum_age=20, maximum_age=70)
    verified = fake.boolean()

    artists_csv_data.append(
        {
            "name": artist_name,
            "name_normalized": normalize_text(artist_name),
            "birth_date": birth_date,
            "verified": verified,
            "description": fake.text(max_nb_chars=50).replace("\n", " "),
            "image_url": image_url,
        }
    )

    albums = get_albums_by_artist(artist_spotify_id)

    if not albums:
        continue

    for album in albums:
        album_counter += 1
        album_id = album["id"]
        album_title = album["name"]
        album_release_date = album["release_date"]
        album_cover_image = album["images"][0]["url"] if album["images"] else None
        album_link = album["external_urls"]["spotify"]
        album_description = fake.text(max_nb_chars=50).replace("\n", " ")

        songs = get_tracks_by_album(album_id)

        if not songs:
            continue

        total_duration = sum(track["duration_ms"] for track in songs) // 1000
        songs_count = len(songs)

        albums_csv_data.append(
            {
                "title": album_title,
                "title_normalized": normalize_text(album_title),
                "description": album_description,
                "release_date": pd.to_datetime(
                    album_release_date, errors="coerce"
                ).strftime("%Y-%m-%d"),
                "total_duration": str(total_duration // 3600).zfill(2)
                + ":"
                + str((total_duration % 3600) // 60).zfill(2)
                + ":"
                + str(total_duration % 60).zfill(2),
                "songs_count": songs_count,
                "cover_image": album_cover_image,
                "link": album_link,
                "artist_id": artist_id,
            }
        )

        for song in songs:
            song_counter += 1
            song_title = song["name"]
            song_duration = song["duration_ms"] // 1000
            song_release_date = album_release_date
            song_link = song["external_urls"]["spotify"]
            normalized_title = normalize_text(song_title)

            songs_csv_data.append(
                {
                    "title": song_title,
                    "title_normalized": normalized_title,
                    "genre": get_artist_genre(artist_spotify_id),
                    "release_date": pd.to_datetime(
                        song_release_date, errors="coerce"
                    ).strftime("%Y-%m-%d"),
                    "duration": str(song_duration // 60).zfill(2)
                    + ":"
                    + str(song_duration % 60).zfill(2),
                    "cover_image": album_cover_image,
                    "likes": fake.random_int(min=0, max=1000),
                    "times_played": fake.random_int(min=0, max=100000),
                    "album_id": album_counter,
                    "link": song_link,
                }
            )

            artist_songs.append(
                {
                    "artist_id": artist_id,
                    "song_id": song_counter,
                }
            )

artists_df = pd.DataFrame(artists_csv_data)
albums_df = pd.DataFrame(albums_csv_data)
songs_df = pd.DataFrame(songs_csv_data)
artist_songs_df = pd.DataFrame(artist_songs)

artists_df.to_csv("data/artists.csv", index=False, encoding="utf-8")
albums_df.to_csv("data/albums.csv", index=False, encoding="utf-8")
songs_df.to_csv("data/songs.csv", index=False, encoding="utf-8")
artist_songs_df.to_csv("data/artist_songs.csv", index=False, encoding="utf-8")
