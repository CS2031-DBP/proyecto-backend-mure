import spotipy
from spotipy.oauth2 import SpotifyClientCredentials

client_id = "61492e37fc6d4819af6687936f4da95a"
client_secret = "67c809f093374def82109738f8d02644"

sp = spotipy.Spotify(
    auth_manager=SpotifyClientCredentials(
        client_id=client_id, client_secret=client_secret
    )
)


def get_artist_id(artist_name):
    results = sp.search(q=f"artist:{artist_name}", type="artist")
    items = results["artists"]["items"]
    if items:
        return items[0]["id"]
    else:
        return None


def get_artist_details(artist_id):
    artist = sp.artist(artist_id)
    image_url = artist["images"][0]["url"] if artist["images"] else None
    return image_url


def get_albums_by_artist(artist_id, limit=10):
    albums = sp.artist_albums(artist_id, limit=limit, album_type="album,single")
    return albums["items"]


def get_tracks_by_album(album_id, artist_name, limit=10):
    tracks = sp.album_tracks(album_id, limit=limit)
    return tracks["items"]
