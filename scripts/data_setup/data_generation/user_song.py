import random
from typing import Any

from data_setup.utils.write_to_csv import write_to_csv


def generate_user_song_data(
    rows_amount: int, user_keys: tuple[int, ...], song_keys: tuple[int, ...]
) -> None:
    user_songs_csv_list: list[dict[str, Any]] = []

    for _ in range(rows_amount):
        user_id = random.choice(user_keys)
        song_id = random.choice(song_keys)

        while (song_id, user_id) in user_songs_csv_list:
            song_id = random.choice(song_keys)

        user_songs_csv_list.append({"user_id": user_id, "song_id": song_id})

    write_to_csv(user_songs_csv_list, "user_songs")
