package ashutosh.chatwisetask.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ashutosh.chatwisetask.repository.ImageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(private val imageRepository: ImageRepository) : ViewModel() {
    val imageResponse get() = imageRepository.imagesResponse

    fun getImages(){
        viewModelScope.launch {
            imageRepository.getImages()
        }
    }
}