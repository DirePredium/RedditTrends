package com.direpredium.reddittrends.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.FragmentPostDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PostDetailsFragment : Fragment(R.layout.fragment_post_details) {

    private lateinit var binding: FragmentPostDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentPostDetailsBinding.bind(view)
        val postId = requireArguments().getString("postId")
    }

}