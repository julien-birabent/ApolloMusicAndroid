package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.CriteriaEntity
import julienbirabent.apollomusic.data.local.entities.ObjectiveEntity
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ObjectiveAPI {

    @FormUrlEncoded
    @POST("/api/objectives")
    fun postObjective(
        @Body objectiveEntity: ObjectiveEntity
    ): Single<Response<ObjectiveEntity>>
}