package julienbirabent.apollomusic.data.api.services

import androidx.lifecycle.LiveData
import julienbirabent.apollomusic.data.api.network.ApiResponse
import julienbirabent.apollomusic.data.local.entities.UserEntity
import retrofit2.http.GET
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface UserAPI {

    //TODO() complete UserApi endpoints when available
    @GET("users/{id}")
    fun getUser(@Path("user") id : String) : LiveData<ApiResponse<UserEntity>>
}