package julienbirabent.apollomusic.data.api.services

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import julienbirabent.apollomusic.data.api.network.ApiResponse
import julienbirabent.apollomusic.data.local.entities.ExampleEntity
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ExampleService{

    @GET(".")
    fun testIfServerRunning() : LiveData<ApiResponse<ExampleEntity>>

    @GET(".")
    fun getSingleExample(): Observable<Response<ExampleEntity>>
}