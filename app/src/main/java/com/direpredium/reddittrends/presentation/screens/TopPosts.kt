package com.direpredium.reddittrends.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.FragmentTopPostsBinding

class TopPosts : Fragment(R.layout.fragment_top_posts) {

    private lateinit var binding: FragmentTopPostsBinding

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