import random
from typing import Any

from scripts.data_setup.utils.shared_faker import faker
from scripts.data_setup.utils.write_to_csv import write_to_csv


def generate_post_data(
    rows_amount: int,
    user_keys: tuple[int, ...],
    song_keys: tuple[int, ...],
    album_keys: tuple[int, ...],
) -> None:
    post_csv_list: list[dict[str, Any]] = []
    post_likes_csv_list: list[dict[str, Any]] = []

    for _ in range(rows_amount):
        likes = random.randint(0, 100)
        created_at = faker.date_time_between(start_date="-1y", end_date="now").strftime(
            "%Y-%m-%d %H:%M:%S"
        )
        user_id = random.choice(user_keys)
        description = faker.text(max_nb_chars=50).replace("\n", " ")
        audio_url = None
        image_url = faker.image_url() if random.choice([True, False]) else None

        if random.choice([True, False]):
            song_id = random.choice(song_keys)
            album_id = None
        else:
            song_id = None
            album_id = random.choice(album_keys)

        post_csv_list.append(
            {
                "likes": likes,
                "created_at": created_at,
                "user_id": user_id,
                "description": description,
                "audio_url": audio_url,
                "image_url": image_url,
                "song_id": song_id,
                "album_id": album_id,
            }
        )

        for _ in range(likes):
            post_likes_csv_list.append(
                {
                    "post_id": len(post_csv_list),
                    "user_id": random.choice(user_keys),
                }
            )

    write_to_csv(post_csv_list, "data/posts.csv")
    write_to_csv(post_likes_csv_list, "data/post_likes.csv")
