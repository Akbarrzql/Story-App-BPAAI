package com.example.storyapp.view.auth.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import com.example.storyapp.databinding.ActivitySpalshScreenBinding
import com.example.storyapp.view.auth.MainAuthActivity
import com.example.storyapp.view.home.MainActivity
import com.example.storyapp.viewmodel.auth.splash_screen.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class SpalshScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        splashScreen()
    }

    private fun splashScreen() {
        lifecycleScope.launch {
            launch {
                viewModel.getAuthToken().collect { token ->
                    if (token.isNullOrEmpty()) {
                        goIntent(MainAuthActivity::class.java)
                    } else {
                        goIntent(MainActivity::class.java)
                    }
                }
            }
        }
    }

    private fun goIntent(clazz: Class<*>) {
        binding.ivSplashScreen.apply {
            alpha = 0f
            animate().setDuration(6500).alpha(1f).withEndAction {
                Intent(this@SpalshScreenActivity, clazz).also { intent ->
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}