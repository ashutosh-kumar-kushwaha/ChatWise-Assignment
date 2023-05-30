package ashutosh.chatwisetask.ui.images

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ashutosh.chatwisetask.adapter.ImagesRecyclerAdapter
import ashutosh.chatwisetask.api.NetworkResult
import ashutosh.chatwisetask.databinding.FragmentImagesBinding
import ashutosh.chatwisetask.databinding.ProgressBarBinding
import ashutosh.chatwisetask.viewModel.ImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImagesFragment : Fragment() {

    private var _binding : FragmentImagesBinding? = null
    private val binding: FragmentImagesBinding get() = _binding!!

    private val imageViewModel by viewModels<ImageViewModel>()

    private val imagesRecyclerAdapter = ImagesRecyclerAdapter()

    private lateinit var progressBar: Dialog
    private var _progressBarBinding : ProgressBarBinding? = null
    private val progressBarBinding get() = _progressBarBinding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentImagesBinding.inflate(inflater, container, false)

        binding.imagesRecyclerView.adapter = imagesRecyclerAdapter
        binding.imagesRecyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        imageViewModel.getImages()

        _progressBarBinding = ProgressBarBinding.inflate(layoutInflater)
        progressBar = Dialog(binding.root.context)
        progressBar.setContentView(progressBarBinding.root)
        progressBar.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressBar.setCanceledOnTouchOutside(false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        imageViewModel.imageResponse.observe(viewLifecycleOwner){
            when (it){
                is NetworkResult.Success -> {
                    imagesRecyclerAdapter.submitList(it.data?.take(50))   // Taking only 50 images to optimize Recycler View
                    progressBar.dismiss()
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                    progressBar.dismiss()
                }
                is NetworkResult.Loading -> {
                    progressBar.show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}