package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.PracticeEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
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


}