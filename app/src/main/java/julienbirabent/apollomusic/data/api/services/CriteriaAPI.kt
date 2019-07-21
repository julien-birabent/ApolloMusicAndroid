package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.model.PostCriteria
import retrofit2.Response
import retrofit2.http.*
import javax.inject.Singleton

@Singleton
interface CriteriaAPI {

    /*@FormUrlEncoded
    @POST("/api/criteria")
    fun postCriteria(
        @Field("criteria") criteria: String?,
        @Field("profileId") profileId: Int?
    ): Single<Response<CriteriaEntity>>*/

    @POST("/api/criteria")
    fun postCriteria(
        @Body criteria: PostCriteria
    ): Single<Response<CriteriaEntity>>

    @GET("/api/criteria")
    fun getAllCriterias(): Single<Response<List<CriteriaEntity>>>
}