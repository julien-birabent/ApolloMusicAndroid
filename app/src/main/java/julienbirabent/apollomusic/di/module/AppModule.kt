package julienbirabent.apollomusic.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.Module
import dagger.Provides
import julienbirabent.apollomusic.R
import julienbirabent.apollomusic.app.AppConstants
import julienbirabent.apollomusic.thread.AppExecutors
import julienbirabent.apollomusic.thread.AppSchedulerProvider
import julienbirabent.apollomusic.thread.SchedulerProvider
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton


@Module
class AppModule {

    @Singleton
    @Provides
    fun provideDateFormatter(): SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.US)

    @Singleton
    @Provides
    fun provideCalendar(): Calendar = Calendar.getInstance()

    @Singleton
    @Provides
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context, appConstants: AppConstants): SharedPreferences {
        return context.getSharedPreferences(appConstants.sharedPrefName(), Context.MODE_PRIVATE)
    }

    @Singleton
    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = AppSchedulerProvider()

    @Singleton
    @Provides
    fun provideAppExecutor(): AppExecutors = AppExecutors()


    @Singleton
    @Provides
    fun provideGoogleSignInOptions(context: Context): GoogleSignInOptions =
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.web_client_id))
            .requestEmail()
            .build()

    @Singleton
    @Provides
    fun provideGoogleSignInClient(context: Context, gso: GoogleSignInOptions): GoogleSignInClient =
        GoogleSignIn.getClient(context, gso)

    @Singleton
    @Provides
    fun provideFacebookCallbackManager(): CallbackManager = CallbackManager.Factory.create()
}

