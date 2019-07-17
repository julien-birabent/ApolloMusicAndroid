package julienbirabent.apollomusic.data.api.network

import android.content.SharedPreferences
import julienbirabent.apollomusic.data.repository.UserRepository
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthInterceptor @Inject constructor(private val sharedPreferences: SharedPreferences) : Interceptor {

    companion object {
        const val ACCESS_TOKEN_KEY = "access_token"
    }

    private fun getLoggedUserToken(): String? {
        return sharedPreferences.getString(UserRepository.key_user_token, null)
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val requestBuilder = request.newBuilder()
        val token = getLoggedUserToken()

        if (token != null) {
            requestBuilder.addHeader("authorization", token)
        }

        return chain.proceed(requestBuilder.build())
    }

}