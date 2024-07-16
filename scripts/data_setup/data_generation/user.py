from typing import Any

from data_setup.constants.file_names import FILE_NAMES
from data_setup.utils.get_random_image_url import get_random_image_url
from data_setup.utils.normalize_text import normalize_text
from data_setup.utils.shared_faker import faker
from data_setup.utils.write_to_csv import write_to_csv


def generate_user_data(rows_amount: int) -> tuple:
    user_keys: list[int] = []
    users_csv_list: list[dict[str, Any]] = []

    for user_id in range(rows_amount):
        user_role = 1
        user_name = faker.name()
        user_nickname = faker.unique.user_name()
        user_nickname_normalized = normalize_text(user_nickname)
        user_email = faker.unique.email()
        user_password = faker.unique.password()
        user_birth_date = faker.date_of_birth(minimum_age=10, maximum_age=70)
        user_created_at = faker.date_time_this_year()
        user_profile_image_url = get_random_image_url(
            FILE_NAMES["default_profile_image"]
        )

        users_csv_list.append(
            {
                "role": user_role,
                "name": user_name,
                "nickname": user_nickname,
                "nickname_normalized": user_nickname_normalized,
                "email": user_email,
                "password": user_password,
                "birth_date": user_birth_date,
                "created_at": user_created_at,
                "profile_image_url": user_profile_image_url,
            }
        )

        user_keys.append(user_id + 1)

    write_to_csv(users_csv_list, "users")

    return tuple(user_keys)
