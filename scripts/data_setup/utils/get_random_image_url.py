import os
import random


def get_random_image_url(*file_names: str) -> str:
    directory_path = "assets/images"
    filenames = os.listdir(directory_path)
    filenames.extend(file_names)
    random_filename = random.choice(filenames)
    return f"https://mure-bucket.s3.amazonaws.com/{random_filename}"
