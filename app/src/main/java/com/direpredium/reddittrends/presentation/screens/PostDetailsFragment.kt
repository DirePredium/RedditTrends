package com.direpredium.reddittrends.presentation.screens

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.FragmentPostDetailsBinding
import com.direpredium.reddittrends.domain.models.api.AsyncResult
import com.direpredium.reddittrends.domain.models.api.ErrorResult
import com.direpredium.reddittrends.domain.models.api.PendingResult
import com.direpredium.reddittrends.domain.models.api.SuccessResult
import com.direpredium.reddittrends.domain.models.storage.FileFormat
import com.direpredium.reddittrends.domain.models.storage.PhotoParams
import com.direpredium.reddittrends.presentation.viewmodel.PostDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


const val ARG_NAME = "name"
const val ARG_IMAGE_URL = "imageUrl"

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private lateinit var binding: FragmentPostDetailsBinding
    private val viewModel: PostDetailsViewModel by viewModels()
    private lateinit var connectivityManager: ConnectivityManager
    private lateinit var networkCallback: ConnectivityManager.NetworkCallback
    private lateinit var postName: String
    private lateinit var postImageUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailsBinding.bind(view)
        initParams()
        setViewLoadState(PendingResult())
        loadPostPhoto(binding.ivPhoto, postImageUrl)
        setupNetworkCallback()
        binding.bSave.setOnClickListener {
            observePosts()
            saveImageToGallery(postName, postImageUrl)
        }
    }

    private fun observePosts() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.savePhotoState.collectLatest {
                when (it) {
                    is PendingResult -> {
                        binding.progressBarSavePhoto.visibility = View.VISIBLE
                        binding.bSave.text = ""
                    }
                    is SuccessResult -> {
                        binding.progressBarSavePhoto.visibility = View.GONE
                        binding.bSave.text = getString(R.string.save_to_gallery)
                        Toast.makeText(requireContext(), getString(R.string.save_success), Toast.LENGTH_SHORT).show()
                    }
                    is ErrorResult -> {
                        binding.progressBarSavePhoto.visibility = View.GONE
                        binding.bSave.text = getString(R.string.save_to_gallery)
                        Toast.makeText(requireContext(), getString(R.string.save_fail), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun initParams() {
        postName = requireArguments().getString(ARG_NAME) ?: throw IllegalArgumentException("No provided parameter")
        postImageUrl = requireArguments().getString(ARG_IMAGE_URL) ?: throw IllegalArgumentException("No provided parameter")
    }

    private fun saveImageToGallery(postName: String, postImageUrl: String) {
        val photoParams = PhotoParams(
            url = postImageUrl,
            fileName = postName,
            format = FileFormat.JPEG
        )
        viewModel.savePhoto(photoParams)
    }

    private fun loadPostPhoto(imageView: ImageView, url: String?) {
        val context = imageView.context
        if (url.isNullOrBlank()) {
            Glide.with(context)
                .load(R.drawable.ic_image_placeholder)
                .into(imageView)
        } else {
            Glide.with(context)
                .load(url)
                .listener(object : RequestListener<Drawable> {

                    override fun onResourceReady(
                        resource: Drawable,
                        model: Any,
                        target: Target<Drawable>?,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        setViewLoadState(SuccessResult("url"))
                        return false
                    }

                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>,
                        isFirstResource: Boolean
                    ): Boolean {
                        val exception = e ?: IllegalStateException("Unexpected exception")
                        setViewLoadState(ErrorResult(exception))
                        return false
                    }
                })
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_error)
                .into(imageView)
        }
    }



    private fun setViewLoadState(asyncResult: AsyncResult<String>) {
        when(asyncResult) {
            is SuccessResult -> {
                binding.scrollBar.visibility = View.VISIBLE
                binding.progressBarLoadDetails.visibility = View.GONE
            }
            is PendingResult -> {
                binding.scrollBar.visibility = View.INVISIBLE
                binding.progressBarLoadDetails.visibility = View.VISIBLE
            }
            is ErrorResult -> {
                binding.scrollBar.visibility = View.INVISIBLE
                binding.progressBarLoadDetails.visibility = View.VISIBLE
            }
        }
    }

    private fun setupNetworkCallback() {
        connectivityManager = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                if (postImageUrl != null) {
                    requireActivity().runOnUiThread {
                        loadPostPhoto(binding.ivPhoto, postImageUrl)
                    }
                }
            }
        }

        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

}