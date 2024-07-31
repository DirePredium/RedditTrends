package com.direpredium.reddittrends.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.FragmentTopPostsBinding
import com.direpredium.reddittrends.presentation.viewmodel.TopPostsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopPostsFragment : Fragment(R.layout.fragment_top_posts) {

    private lateinit var binding: FragmentTopPostsBinding
    private val viewModel: TopPostsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopPostsBinding.bind(view)
    }

    private fun openDetails() {
        findNavController().navigate(
            R.id.action_topPosts_to_postDetails,
            bundleOf("postId" to "1")
        )
    }

}