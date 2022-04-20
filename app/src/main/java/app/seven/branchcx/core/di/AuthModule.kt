package app.seven.branchcx.core.di

import app.seven.branchcx.core.utils.Constants
import app.seven.branchcx.modules.auth.data.data_source.remote.LoginApi
import app.seven.branchcx.modules.auth.data.repo.LoginRepo
import app.seven.branchcx.modules.auth.data.repo.LoginRepoImpl
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Singleton
    @Provides
    fun provideLoginApi(): LoginApi {

        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .client(client)
            .baseUrl(Constants.BASE_URL)
            .build().create(LoginApi::class.java)
    }

    @Singleton
    @Provides
    fun provideLoginRepo(api: LoginApi): LoginRepo {
        return LoginRepoImpl(api = api)
    }

}