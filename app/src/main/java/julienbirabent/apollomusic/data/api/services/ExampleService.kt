package julienbirabent.apollomusic.data.api.services

import androidx.lifecycle.LiveData
import io.reactivex.Observable
import io.reactivex.Single
import julienbirabent.apollomusic.data.api.network.ApiResponse
import julienbirabent.apollomusic.data.local.entities.Example
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import javax.inject.Singleton

@Singleton
interface ExampleService{

    @GET(".")
    fun testIfServerRunning() : LiveData<ApiResponse<Example>>

    @GET(".")
    fun getSingleExample(): Observable<Response<Example>>
}