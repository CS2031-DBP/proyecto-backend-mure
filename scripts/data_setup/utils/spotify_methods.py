from typing import Any

import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

client_id = "61492e37fc6d4819af6687936f4da95a"
client_secret = "67c809f093374def82109738f8d02644"

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


def get_artist_details(artist_id: str):
    artist: Any = sp.artist(artist_id)
    image_url = artist["images"][0]["url"] if artist["images"] else None
    return image_url


def get_albums_by_artist(artist_id: str, limit=10):
    albums: Any = sp.artist_albums(artist_id, limit=limit, album_type="album,single")
    return albums["items"]


def get_tracks_by_album(album_id: str, limit=10):
    tracks: Any = sp.album_tracks(album_id, limit=limit)
    tracks_with_preview = [track for track in tracks["items"] if track["preview_url"]]
    return tracks_with_preview
