package ashutosh.chatwisetask.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ashutosh.chatwisetask.api.NetworkResult
import ashutosh.chatwisetask.api.RetrofitAPI
import ashutosh.chatwisetask.models.Image
import javax.inject.Inject

class ImageRepository @Inject constructor(private val retrofitAPI: RetrofitAPI){

    private val _imagesResponse = MutableLiveData<NetworkResult<List<Image>>>()
    val imagesResponse : LiveData<NetworkResult<List<Image>>> get() = _imagesResponse

    suspend fun getImages(){
        _imagesResponse.value = NetworkResult.Loading()
        try {
            val response = retrofitAPI.getImages()
            when(response.code()){
                200 -> {
                    if(response.body()!=null){
                        _imagesResponse.value = NetworkResult.Success(200, response.body()!!)
                    }
                    else{
                        _imagesResponse.value = NetworkResult.Error(200, "Something went wrong\nResponse in null")
                    }
                }
                else -> {
                    _imagesResponse.value = NetworkResult.Error(response.code(), "Something went wrong\nError code: ${response.code()}")
                }
            }
        } catch (e: Exception){
            _imagesResponse.value = NetworkResult.Error(-1, e.message)
            e.printStackTrace()
        }
    }
}