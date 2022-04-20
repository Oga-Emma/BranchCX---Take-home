package app.seven.branchcx.core.di

import app.seven.branchcx.core.service.cache.LocalCache
import app.seven.branchcx.core.utils.AuthTokenInterceptor
import app.seven.branchcx.core.utils.Constants
import app.seven.branchcx.modules.auth.data.data_source.remote.LoginApi
import app.seven.branchcx.modules.auth.data.repo.LoginRepo
import app.seven.branchcx.modules.auth.data.repo.LoginRepoImpl
import app.seven.branchcx.modules.messaging.data.data_source.local.MessageDao
import app.seven.branchcx.modules.messaging.data.data_source.remote.MessagingApi
import app.seven.branchcx.modules.messaging.data.repo.MessagingRepo
import app.seven.branchcx.modules.messaging.data.repo.MessagingRepoImpl
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object MessagingModule {
    @Singleton
    @Provides
    fun provideLoginApi(cache: LocalCache): MessagingApi {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(AuthTokenInterceptor(cache))
            .build()

        val gson = GsonBuilder()
//            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(ZonedDateTime::class.java, object : JsonDeserializer<ZonedDateTime>{
            override fun deserialize(
                json: JsonElement?,
                typeOfT: Type?,
                context: JsonDeserializationContext?
            ): ZonedDateTime {
                try{
                    if(json != null){
                        return  OffsetDateTime.parse(json.asString).toZonedDateTime()
                    }

                    return ZonedDateTime.now()
                }catch (err: Exception){
                    Timber.d("Failed to deserialize $json")
                    return ZonedDateTime.now()
                }
            }
        }).create()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build().create(MessagingApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepo(api: MessagingApi, db: MessageDao): MessagingRepo {
        return MessagingRepoImpl(api, db)
    }

}