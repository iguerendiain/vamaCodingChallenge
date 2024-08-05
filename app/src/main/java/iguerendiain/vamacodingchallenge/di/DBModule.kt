package iguerendiain.vamacodingchallenge.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import iguerendiain.vamacodingchallenge.model.Album
import iguerendiain.vamacodingchallenge.model.Genre
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
                Album::class,
                Genre::class
            )
        )

        return Realm.open(config)
    }
}