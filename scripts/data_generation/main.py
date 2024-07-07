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
            "id": artist_id,
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

        tracks = get_tracks_by_album(album_id, artist_name, limit=10)
        if not tracks:
            continue

        total_duration = sum(track["duration_ms"] for track in tracks) // 1000
        songs_count = len(tracks)

        albums_csv_data.append(
            {
                "id": album_counter,
                "title": album_title,
                "title_normalized": normalize_text(album_title),
                "description": album_description,
                "release_date": album_release_date,
                "total_duration": str(total_duration // 3600).zfill(2) + ":"
                                  + str((total_duration % 3600) // 60).zfill(2)
                                  + ":"
                                  + str(total_duration % 60).zfill(2),
                "songs_count": songs_count,
                "cover_image": album_cover_image,
                "link": album_link,
                "artist_id": artist_id,
            }
        )

        for track in tracks:
            song_counter += 1
            track_title = track["name"]
            track_duration = track["duration_ms"] // 1000  # Convertir a segundos
            track_release_date = album_release_date
            track_link = track["external_urls"]["spotify"]
            normalized_title = normalize_text(track_title)

            songs_csv_data.append(
                {
                    "id": song_counter,
                    "title": track_title,
                    "title_normalized": normalized_title,
                    "genre": "Unknown",
                    "release_date": track_release_date,
                    "duration": str(track_duration // 60).zfill(2) + ":" + str(track_duration % 60).zfill(2),
                    "cover_image": album_cover_image,
                    "likes": fake.random_int(min=0, max=1000),
                    "times_played": fake.random_int(min=0, max=100000),
                }
            )

artists_df = pd.DataFrame(artists_csv_data)
albums_df = pd.DataFrame(albums_csv_data)
songs_df = pd.DataFrame(songs_csv_data)

artists_df.to_csv("data/artists.csv", index=False, encoding='utf-8')
albums_df.to_csv("data/albums.csv", index=False, encoding='utf-8')
songs_df.to_csv("data/songs.csv", index=False, encoding='utf-8')
