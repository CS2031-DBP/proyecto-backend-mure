from typing import Any

import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

client_id = "eedbe20cd6234f6cba3a6364d1b527a7"
client_secret = "41b7f5ce89524a03bb41f099afc21224"

sp = spotipy.Spotify(
    auth_manager=SpotifyClientCredentials(
        client_id=client_id, client_secret=client_secret
    )
)


def get_artist_id(artist_name: str):
    results: Any = sp.search(q=f"artist:{artist_name}", type="artist")
    items = results["artists"]["items"]

    if items:
        return items[0]["id"]
    else:
        return None


def get_artist_genre(artist_id: str):
    result: Any = sp.artist(artist_id)
    return result["genres"][0] if result["genres"] else None


def get_artist_details(artist_id: str):
    artist: Any = sp.artist(artist_id)
    image_url = artist["images"][0]["url"] if artist["images"] else None
    return image_url


def get_albums_by_artist(artist_id: str, limit=10):
    albums: Any = sp.artist_albums(artist_id, limit=limit, album_type="album,single")
    return albums["items"]


def get_tracks_by_album(album_id: str, limit=10):
    tracks: Any = sp.album_tracks(album_id, limit=limit)
    return tracks["items"]
