import pandas as pd

from scripts.data_setup.utils.normalize_text import normalize_text
from scripts.data_setup.utils.shared_faker import faker
from scripts.data_setup.utils.spotify_methods import *
from scripts.data_setup.utils.write_to_csv import write_to_csv


def generate_artists_songs_albums(
    artists_dict: dict[int, str]
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

    for artist_id, artist_name in artists_dict.items():
        print(artist_name)
        artist_count += 1
        artist_spotify_id = get_artist_id(artist_name)

        if not artist_spotify_id:
            continue

        image_url = get_artist_details(artist_spotify_id)
        birth_date = faker.date_of_birth(minimum_age=20, maximum_age=70)
        verified = faker.boolean()

        artists_csv_list.append(
            {
                "name": artist_name,
                "name_normalized": normalize_text(artist_name),
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
            album_cover_image = album["images"][0]["url"] if album["images"] else None
            album_link = album["external_urls"]["spotify"]
            album_description = faker.text(max_nb_chars=50).replace("\n", " ")

            songs = get_tracks_by_album(album_id)

            if not songs:
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
                    "cover_image": album_cover_image,
                    "link": album_link,
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
                normalized_title = normalize_text(song_title)

                songs_csv_list.append(
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
                        "likes": faker.random_int(min=0, max=1000),
                        "times_played": faker.random_int(min=0, max=100000),
                        "album_id": album_counter,
                        "link": song_link,
                    }
                )
                songs_keys.append(song_counter)

                artist_songs_csv_list.append(
                    {
                        "artist_id": artist_id,
                        "song_id": song_counter,
                    }
                )

    write_to_csv(artists_csv_list, "data/artists.csv")
    write_to_csv(albums_csv_list, "data/albums.csv")
    write_to_csv(songs_csv_list, "data/songs.csv")
    write_to_csv(artist_songs_csv_list, "data/artist_songs.csv")

    return tuple(artists_keys), tuple(albums_keys), tuple(songs_keys)
