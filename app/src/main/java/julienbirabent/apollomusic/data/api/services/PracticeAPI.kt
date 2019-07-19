package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.ObjectiveCriteriaJoin
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveExerciseJoin
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
    fun getUserPractices(@Path("id") profileId: String?): Single<Response<List<PracticeEntity>>>

    @GET("/api/practices/{id}/objectives")
    fun getObjectiveWithPracticeId(@Path("id") practiceId: String?): Single<Response<List<ObjectiveEntity>>>

    @GET("/api/objectives/{id}/mappingCriteriaObjectifs")
    fun getObjectiveCriteriaJoin(@Path("id") objId: Int?): Single<List<ObjectiveCriteriaJoin>>

    @GET("/api/objectives/{id}/mappingObjectifExercices")
    fun getObjectiveExerciseJoin(@Path("id") exerciseId: Int?): Single<List<ObjectiveExerciseJoin>>
}