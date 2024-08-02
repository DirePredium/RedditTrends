package com.direpredium.reddittrends.presentation.screens

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.FragmentTopPostsBinding
import com.direpredium.reddittrends.presentation.adapters.PostsAdapter
import com.direpredium.reddittrends.presentation.adapters.PostsLoadStateAdapter
import com.direpredium.reddittrends.presentation.adapters.TryAgainAction
import com.direpredium.reddittrends.presentation.viewmodel.TopPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopPostsFragment : Fragment(R.layout.fragment_top_posts) {

    private lateinit var binding: FragmentTopPostsBinding
    private val viewModel: TopPostsViewModel by viewModels()
    private lateinit var mainLoadStateHolder: PostsLoadStateAdapter.Holder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTopPostsBinding.bind(view)

        setupUsersList()
        setupSwipeToRefresh()
    }

    private fun openDetails() {
        findNavController().navigate(
            R.id.action_topPosts_to_postDetails,
            bundleOf("postId" to "1")
        )
    }

    private fun setupUsersList() {
        val adapter = PostsAdapter()
        val tryAgainAction: TryAgainAction = { adapter.retry() }
        val footerAdapter = PostsLoadStateAdapter(tryAgainAction)
        val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)

        binding.postsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.postsRecyclerView.adapter = adapterWithLoadState
        (binding.postsRecyclerView.itemAnimator as? DefaultItemAnimator)?.supportsChangeAnimations =
            false

        mainLoadStateHolder = PostsLoadStateAdapter.Holder(
            binding.loadStateView,
            binding.swipeRefreshLayout,
            tryAgainAction
        )

        observePosts(adapter)
        observeLoadState(adapter)

        handleListVisibility(adapter)
    }

    private fun setupSwipeToRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun observePosts(adapter: PostsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.postsFlow.collectLatest { pagingData ->
                adapter.submitData(pagingData)
            }
        }
    }

    private fun observeLoadState(adapter: PostsAdapter) {
        viewLifecycleOwner.lifecycleScope.launch {
            adapter.loadStateFlow.debounce(200)
                .collectLatest { state ->
                    mainLoadStateHolder.bind(state.refresh)
                }
        }
    }


    private fun handleListVisibility(adapter: PostsAdapter) = lifecycleScope.launch {
        getRefreshLoadStateFlow(adapter)
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.postsRecyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun getRefreshLoadStateFlow(adapter: PostsAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }

}

fun <T> Flow<T>.simpleScan(count: Int): Flow<List<T?>> {
    val items = List<T?>(count) { null }
    return this.scan(items) { previous, value -> previous.drop(1) + value }
}