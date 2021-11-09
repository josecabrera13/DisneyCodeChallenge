package com.example.disneycodechallenge

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.disneycodechallenge.databinding.ActivityDisneyCodeChallengeBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DisneyCodeChallengeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDisneyCodeChallengeBinding
    private var snackBar: Snackbar? = null
    private val viewModel: DisneyCodeChallengeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDisneyCodeChallengeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolBar.setupWithNavController(navController, appBarConfiguration)
        viewModel.onErrorEvent.observe(this) {
            //TODO We can handle different error types here checking the Throwable object or
            // We can create a Error class depending of the error code.
            showSnackBar(R.string.app_generic_problem)
        }
    }

    private fun showSnackBar(@StringRes idStringRes: Int) {
        //TODO If the user opens the app and the app doesn't have internet we show the error but
        // the app can't have a way to retry the call service. For this issue we can implement
        // the pull to refresh gesture.
        if (snackBar == null) {
            snackBar = Snackbar.make(binding.appContainer, idStringRes, Snackbar.LENGTH_SHORT)
        }
        snackBar?.dismiss()
        snackBar?.setText(idStringRes)
        snackBar?.show()
    }
}