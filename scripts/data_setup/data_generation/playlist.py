import random
from typing import Any

from data_setup.utils.shared_faker import faker
from data_setup.utils.write_to_csv import write_to_csv


def generate_playlist_data(
    rows_amount: int, user_keys: tuple[int, ...], song_keys: tuple[int, ...]
):
    playlist_csv_list: list[dict[str, Any]] = []
    playlist_songs_csv_list: list[dict[str, Any]] = []

    for _ in range(rows_amount):
        user_id = random.choice(user_keys)
        name = faker.text(max_nb_chars=50).replace("\n", " ")

        playlist_csv_list.append(
            {
                "user_id": user_id,
                "name": name,
            }
        )

        for _ in range(random.randint(1, 100)):
            playlist_songs_csv_list.append(
                {
                    "playlist_id": len(playlist_csv_list),
                    "song_id": random.choice(song_keys),
                }
            )

    write_to_csv(playlist_csv_list, "playlists")
    write_to_csv(playlist_songs_csv_list, "playlist_songs")
