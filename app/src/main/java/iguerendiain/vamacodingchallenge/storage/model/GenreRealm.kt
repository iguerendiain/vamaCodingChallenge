package iguerendiain.vamacodingchallenge.storage.model

import iguerendiain.vamacodingchallenge.model.Genre
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class GenreRealm: RealmObject {
    @PrimaryKey
    var genreId: Int = -1
    var name: String = ""
    var url: String = ""

    fun toModel() = toModel(this)

    companion object {
        fun fromModel(genre: Genre): GenreRealm {
            return GenreRealm().apply {
                genreId = genre.genreId
                name = genre.name
                url = genre.url
            }
        }

        fun toModel(genreRealm: GenreRealm): Genre {
            return Genre(
                genreId = genreRealm.genreId,
                name = genreRealm.name,
                url = genreRealm.url
            )
        }
    }
}