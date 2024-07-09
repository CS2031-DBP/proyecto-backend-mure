import csv


def write_to_csv(data_list: list, file_path: str) -> None:
    if not data_list:
        raise ValueError("The data list is empty")

    headers = data_list[0].keys()

    with open(file_path, mode="w", newline="", encoding="utf-8") as file:
        writer = csv.DictWriter(file, fieldnames=headers)
        writer.writeheader()
        writer.writerows(data_list)
