package me.darthwithap.recipeapp.presentation.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.runtime.ExperimentalComposeApi
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.recipeapp.R
import me.darthwithap.recipeapp.presentation.ui.BaseApplication
import javax.inject.Inject

private const val TAG = "MainActivity"

@ExperimentalComposeApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var app: BaseApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: BASE APPLICATION: $app")

    }
}