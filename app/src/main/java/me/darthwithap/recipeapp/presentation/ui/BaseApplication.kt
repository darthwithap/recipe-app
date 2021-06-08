package me.darthwithap.recipeapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    val isDarkTheme = mutableStateOf(false)

    fun toggleTheme() {
        isDarkTheme.value = !isDarkTheme.value
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var context: Context? = null
        fun getContext(): Context {
            return context!!
        }
    }

    override fun onCreate() {
        super.onCreate()
        context = this
    }

}