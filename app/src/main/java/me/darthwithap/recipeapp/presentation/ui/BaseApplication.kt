package me.darthwithap.recipeapp.presentation.ui

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
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