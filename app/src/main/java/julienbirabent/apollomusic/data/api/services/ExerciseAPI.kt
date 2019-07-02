package julienbirabent.apollomusic.data.api.services

import io.reactivex.Single
import julienbirabent.apollomusic.data.local.entities.ExerciseEntity
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ExerciseAPI {

    @GET("api/exercices")
    fun getAllExercises(): Single<Response<List<ExerciseEntity>>>

}
