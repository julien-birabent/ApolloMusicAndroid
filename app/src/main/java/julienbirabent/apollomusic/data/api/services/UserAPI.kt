package julienbirabent.apollomusic.data.api.services


import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.UserEntity
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface UserAPI {

    @FormUrlEncoded
    @POST("/api/profiles/tokenSignin")
    fun login(
        @Field("token") providerToken: String?,
        @Field("provider") nameOfProvider: String?
    ): Single<Response<UserEntity>>
}