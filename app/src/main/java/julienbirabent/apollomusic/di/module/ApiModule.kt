package julienbirabent.apollomusic.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.data.api.network.DataTypeAdapterFactory
import julienbirabent.apollomusic.data.api.network.livedataconverter.LiveDataCallAdapterFactory
import julienbirabent.apollomusic.data.api.services.*
import julienbirabent.apollomusic.thread.SchedulerProvider
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
    fun provideRetrofit(
        appConstants: AppConstants,
        client: OkHttpClient,
        gson: Gson,
        scheduler: SchedulerProvider
    ): Retrofit {

        return Retrofit.Builder()
            .baseUrl(appConstants.baseUrl())
            .client(client)
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(scheduler.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .excludeFieldsWithoutExposeAnnotation()
        .registerTypeAdapterFactory(DataTypeAdapterFactory())
        .setLenient()
        .create()


    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

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

    @Provides
    @Singleton
    fun provideExampleApi(retrofit: Retrofit): ExampleAPI {
        return retrofit.create(ExampleAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideCriteriaApi(retrofit: Retrofit): CriteriaAPI {
        return retrofit.create(CriteriaAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideExerciseAPI(retrofit: Retrofit): ExerciseAPI {
        return retrofit.create(ExerciseAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideObjectiveAPI(retrofit: Retrofit): ObjectiveAPI {
        return retrofit.create(ObjectiveAPI::class.java)
    }

    @Provides
    @Singleton
    fun providePracticeAPI(retrofit: Retrofit): PracticeAPI {
        return retrofit.create(PracticeAPI::class.java)
    }
}
