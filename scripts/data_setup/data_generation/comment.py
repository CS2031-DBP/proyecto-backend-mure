import random
from typing import Any

from data_setup.utils.shared_faker import faker
from data_setup.utils.write_to_csv import write_to_csv


def generate_comment_data(
    rows_amount: int, user_keys: tuple[int, ...], post_keys: tuple[int, ...]
) -> None:
    commment_csv_list: list[dict[str, Any]] = []

    for _ in range(rows_amount):
        user_id = random.choice(user_keys)
        post_id = random.choice(post_keys)
        content = faker.text(max_nb_chars=50).replace("\n", " ")
        created_at = faker.date_between(start_date="-1y", end_date="now").strftime(
            "%Y-%m-%d %H:%M:%S"
        )

        commment_csv_list.append(
            {
                "user_id": user_id,
                "post_id": post_id,
                "content": content,
                "created_at": created_at,
            }
        )

    write_to_csv(commment_csv_list, "comments")
