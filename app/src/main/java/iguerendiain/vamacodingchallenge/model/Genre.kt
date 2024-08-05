package iguerendiain.vamacodingchallenge.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class Genre: RealmObject {
    @PrimaryKey var genreId: Int = -1
    var name: String = ""
    var url: String = ""
}
