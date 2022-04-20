package app.seven.branchcx.core.di

import android.content.Context
import android.preference.PreferenceManager
import androidx.room.Database
import androidx.room.Room
import app.seven.branchcx.core.service.cache.LocalCache
import app.seven.branchcx.core.service.db.AppDatabaseRoomImpl
import app.seven.branchcx.core.service.db.LocalCacheSharedPrefImpl
import app.seven.branchcx.core.utils.Constants
import app.seven.branchcx.modules.auth.data.data_source.remote.LoginApi
import app.seven.branchcx.modules.auth.data.repo.LoginRepo
import app.seven.branchcx.modules.auth.data.repo.LoginRepoImpl
import app.seven.branchcx.modules.messaging.data.data_source.local.MessageDao
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, AppDatabaseRoomImpl::class.java, Constants.APP_ROOM_DATABASE).build()

    @Singleton
    @Provides
    fun provideShoppingDao(
        database: AppDatabaseRoomImpl
    ) = database.messageDao()

    @Singleton
    @Provides
    fun provideLocalCache(
        @ApplicationContext context: Context
    ): LocalCache{
        return LocalCacheSharedPrefImpl(
            context.getSharedPreferences(Constants.SHARED_PREFERENCES_TAG, Context.MODE_PRIVATE))
    }
}
