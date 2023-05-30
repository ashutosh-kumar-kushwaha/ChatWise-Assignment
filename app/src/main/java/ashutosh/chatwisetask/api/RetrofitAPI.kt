package ashutosh.chatwisetask.api

import ashutosh.chatwisetask.models.Image
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitAPI {

    @GET("photos")
    suspend fun getImages() : Response<List<Image>>

}