package julienbirabent.apollomusic.data.api.services


import androidx.lifecycle.LiveData
import io.reactivex.Single
import julienbirabent.apollomusic.data.api.network.livedataconverter.ApiResponse
import julienbirabent.apollomusic.data.local.entities.UserEntity
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface UserAPI {

    //TODO() complete UserApi endpoints when available
    @GET("users/{id}")
    fun getUser(@Path("user") id: String): LiveData<ApiResponse<UserEntity>>

    @FormUrlEncoded
    @POST("/api/profiles/tokenSignin")
    fun login(@Field("provider") tokenOfProvider: String?): Single<Response<UserEntity>>
}