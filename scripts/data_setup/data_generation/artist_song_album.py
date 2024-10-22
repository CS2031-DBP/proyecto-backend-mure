import random

import pandas as pd

from data_setup.utils.normalize_text import normalize_text
from data_setup.utils.shared_faker import faker
from data_setup.utils.spotify_methods import *
from data_setup.utils.write_to_csv import write_to_csv


def generate_artists_songs_albums(
    artists_dict: dict[int, dict[str, Any]]
) -> tuple[tuple, tuple, tuple]:
    artists_csv_list: list[dict[str, Any]] = []
    albums_csv_list: list[dict[str, Any]] = []
    songs_csv_list: list[dict[str, Any]] = []
    artist_songs_csv_list: list[dict[str, Any]] = []

    artists_keys: list[int] = []
    albums_keys: list[int] = []
    songs_keys: list[int] = []

    album_counter = 0
    song_counter = 0
    artist_count = 0

    for artist_id, artist_info in artists_dict.items():
        artist_count += 1
        print(artist_info["name"], " ", artist_count)
        artist_spotify_id = get_artist_id(artist_info["name"])

        if not artist_spotify_id:
            continue

        image_url = get_artist_details(artist_spotify_id)
        birth_date = faker.date_of_birth(minimum_age=20, maximum_age=70)
        verified = faker.boolean()

        artists_csv_list.append(
            {
                "name": artist_info["name"],
                "name_normalized": normalize_text(artist_info["name"]),
                "birth_date": birth_date,
                "verified": verified,
                "description": faker.text(max_nb_chars=50).replace("\n", " "),
                "image_url": image_url,
            }
        )
        artists_keys.append(artist_id)

        albums = get_albums_by_artist(artist_spotify_id)

        if not albums:
            continue

        for album in albums:
            album_counter += 1
            album_id = album["id"]
            album_title = album["name"]
            album_release_date = album["release_date"]
            album_cover_image_url = (
                album["images"][0]["url"] if album["images"] else None
            )
            album_link = album["external_urls"]["spotify"]
            album_description = faker.text(max_nb_chars=50).replace("\n", " ")

            songs = get_tracks_by_album(album_id)

            if not songs:
                album_counter -= 1
                continue

            total_duration = sum(track["duration_ms"] for track in songs) // 1000
            songs_count = len(songs)

            albums_csv_list.append(
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
                    "cover_image_url": album_cover_image_url,
                    "spotify_url": album_link,
                    "artist_id": artist_id,
                }
            )
            albums_keys.append(album_counter)

            for song in songs:
                song_counter += 1
                song_title = song["name"]
                song_duration = song["duration_ms"] // 1000
                song_release_date = album_release_date
                song_link = song["external_urls"]["spotify"]
                title_normalized = normalize_text(song_title)
                song_preview_url = song["preview_url"]
                genre = random.choice(artist_info["genre"])

                songs_csv_list.append(
                    {
                        "title": song_title,
                        "title_normalized": title_normalized,
                        "genre": genre,
                        "genre_normalized": normalize_text(genre),
                        "release_date": pd.to_datetime(
                            song_release_date, errors="coerce"
                        ).strftime("%Y-%m-%d"),
                        "duration": str(song_duration // 60).zfill(2)
                        + ":"
                        + str(song_duration % 60).zfill(2),
                        "cover_image_url": album_cover_image_url,
                        "likes": faker.random_int(min=0, max=1000),
                        "times_played": faker.random_int(min=0, max=100000),
                        "album_id": album_counter,
                        "spotify_url": song_link,
                        "spotify_preview_url": song_preview_url,
                    }
                )
                songs_keys.append(song_counter)

                artist_songs_csv_list.append(
                    {
                        "artist_id": artist_id,
                        "song_id": song_counter,
                    }
                )

    write_to_csv(artists_csv_list, "artists")
    write_to_csv(albums_csv_list, "albums")
    write_to_csv(songs_csv_list, "songs")
    write_to_csv(artist_songs_csv_list, "artist_songs")

    return tuple(artists_keys), tuple(albums_keys), tuple(songs_keys)
