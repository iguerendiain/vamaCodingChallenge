package iguerendiain.vamacodingchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iguerendiain.vamacodingchallenge.storage.model.AlbumRealm
import iguerendiain.vamacodingchallenge.storage.model.FeedRealm
import iguerendiain.vamacodingchallenge.storage.model.GenreRealm
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DBModule {

    @Provides
    @Singleton
    fun provideMainDB(): Realm {
        val config = RealmConfiguration.create(
            schema = setOf(
                AlbumRealm::class,
                GenreRealm::class,
                FeedRealm::class
            )
        )

        return Realm.open(config)
    }
}