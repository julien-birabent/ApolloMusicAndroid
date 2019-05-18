package julienbirabent.apollomusic.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(appConstants: AppConstants, client: OkHttpClient, scheduler: AppSchedulerProvider): Retrofit {

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(appConstants.baseUrl())
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler.io()))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().excludeFieldsWithoutExposeAnnotation().create()


    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
    }

}
