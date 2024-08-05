package iguerendiain.vamacodingchallenge.model

import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmInstant
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.util.Date

class Album(): RealmObject {
    var artistName: String = ""
    @PrimaryKey var id: String = ""
    var name: String = ""
    var releaseDate: RealmInstant = RealmInstant.now()
    var kind: String = ""
    var artistId: String = ""
    var artistUrl: String = ""
    var artworkUrl100: String = ""
    var genres: RealmList<Genre> = realmListOf()
    var url: String = ""
}