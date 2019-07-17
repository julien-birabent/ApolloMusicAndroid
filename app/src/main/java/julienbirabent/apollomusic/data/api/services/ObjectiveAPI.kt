package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.ObjectiveCriteriaJoin
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveExerciseJoin
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import retrofit2.http.Path
import javax.inject.Singleton

@Singleton
interface ObjectiveAPI {

    @FormUrlEncoded
    @POST("/api/objectives")
    fun postObjective(
        @Field(value = "objectif") objectif: String? = "",
        @Field(value = "targetPracticeTime") practiceTime: Int? = 0,
        @Field(value = "tempoHistory") tempoHistory: String? = "",
        @Field(value = "tempoBase") tempoBase: Int? = 0,
        @Field(value = "practiceId") practiceId: Int? = -1
    ): Single<Response<ObjectiveEntity>>

    @FormUrlEncoded
    @POST("/api/objectives/{objId}/mappingCriteriaObjectifs")
    fun postObjectiveCriteriaJoin(
        @Path("objId") objId: String,
        @Field(value = "objectiveId") objIdField: Int?,
        @Field(value = "criteriaId") criteriaId: Int?
    ): Single<Response<ObjectiveCriteriaJoin>>

    @FormUrlEncoded
    @POST("/api/objectives/{objId}/mappingObjectifExercices")
    fun postObjectiveExerciseJoin(
        @Path("objId") objId: String,
        @Field(value = "exerciceId") exerciseId: Int?,
        @Field(value = "objectiveId") objIdField: Int?
    ): Single<Response<ObjectiveExerciseJoin>>



}