package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import retrofit2.Response
import retrofit2.http.*
import java.util.*
import javax.inject.Singleton

@Singleton
interface PracticeAPI {

    @FormUrlEncoded
    @POST("/api/practices")
    fun postPractice(
        @Field(value = "datePractice") date: Date?,
        @Field(value = "userNotes") notes: String?,
        @Field(value = "profileId") profileId: String?
    ): Single<Response<PracticeEntity>>


    @GET("/api/profiles/{id}/practices")
    fun getUserPractices(@Path("id") profileId: String?, @Field("access_token") token: String?): Single<Response<List<PracticeEntity>>>
}