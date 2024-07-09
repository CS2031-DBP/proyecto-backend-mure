import random
from typing import Any

from scripts.data_setup.utils.write_to_csv import write_to_csv


def generate_user_friends_data(rows_amount: int, user_keys: tuple[int, ...]):
    user_friends_csv_list: list[dict[str, Any]] = []

    for _ in range(rows_amount):
        user_id = random.choice(user_keys)
        friend_id = random.choice(user_keys)

        while friend_id == user_id:
            friend_id = random.choice(user_keys)

        user_friends_csv_list.append({"user_id": user_id, "friend_id": friend_id})

    write_to_csv(user_friends_csv_list, "data/user_friends.csv")
