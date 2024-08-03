package com.direpredium.reddittrends.presentation

import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.direpredium.reddittrends.R
import com.direpredium.reddittrends.databinding.ActivityMainBinding
import com.direpredium.reddittrends.presentation.screens.ARG_IMAGE_URL
import com.direpredium.reddittrends.presentation.screens.ARG_NAME
import com.direpredium.reddittrends.presentation.viewmodel.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)


        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupActionBarWithNavController(this, navController)
        onBackPressedDispatcher.addCallback(this, object: OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navController.popBackStack()
                viewModel.setPostStateEmpty()
            }
        })

        if(!viewModel.isPostStateRestored) {
            restoreOpenedPostState()
        }
    }

    private fun restoreOpenedPostState() {
        lifecycleScope.launch {
            val openedPostStateDeferred = async { viewModel.getOpenedPostStateAsync() }
            viewModel.isPostStateRestored = true
            val openedPostState = openedPostStateDeferred.await()
            if(openedPostState != null) {
                navController.navigate(
                    R.id.postDetails,
                    bundleOf(ARG_NAME to openedPostState.name, ARG_IMAGE_URL to openedPostState.url)
                )
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        viewModel.setPostStateEmpty()
        return if (navController.navigateUp()) {
            true
        } else {
            super.onSupportNavigateUp()
        }
    }

}