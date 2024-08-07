package iguerendiain.vamacodingchallenge.storage.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class FeedRealm: RealmObject {
    var copyright: String = ""
    @PrimaryKey var id = 1
}