package iguerendiain.vamacodingchallenge.storage.model

import iguerendiain.vamacodingchallenge.model.Album
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class AlbumRealm: RealmObject {
    var artistName: String = ""
    @PrimaryKey
    var id: String = ""
    var name: String = ""
    var releaseDate: String = ""
    var kind: String = ""
    var artistId: String = ""
    var artistUrl: String = ""
    var artworkUrl100: String = ""
    var genres: RealmList<GenreRealm> = realmListOf()
    var url: String = ""

    fun toModel() = toModel(this)

    companion object{
        fun fromModel(album: Album): AlbumRealm {
            return AlbumRealm().apply {
                artistName = album.artistName
                id = album.id
                name = album.name
                releaseDate = album.releaseDate?:""
                kind = album.kind
                artistId = album.artistId
                artistUrl = album.artistUrl
                artworkUrl100 = album.artworkUrl100
                genres = realmListOf<GenreRealm>().apply {
                    addAll(album.genres.map { GenreRealm.fromModel(it) })
                }
                url = album.url
            }
        }

        fun toModel(albumRealm: AlbumRealm): Album {
            return Album(
                artistName = albumRealm.artistName,
                id = albumRealm.id,
                name = albumRealm.name,
                releaseDate = albumRealm.releaseDate,
                kind = albumRealm.kind,
                artistId = albumRealm.artistId,
                artistUrl = albumRealm.artistUrl,
                artworkUrl100 = albumRealm.artworkUrl100,
                genres = albumRealm.genres.map { it.toModel() },
                url = albumRealm.url
            )
        }
    }
}