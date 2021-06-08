package me.darthwithap.recipeapp.presentation.components.util

import androidx.compose.material.ScaffoldState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackbarController(
    private val scope: CoroutineScope
) {
    private var snackbarJob: Job? = null

    init {
        cancelJob()
    }

    fun getScope() = scope

    fun showSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        if (snackbarJob == null) {
            runSnackbar(scaffoldState, message, actionLabel)
        } else {
            cancelJob()
            runSnackbar(scaffoldState, message, actionLabel)
        }
    }

    private fun runSnackbar(
        scaffoldState: ScaffoldState,
        message: String,
        actionLabel: String
    ) {
        snackbarJob = scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(
                message, actionLabel
            )// wait
            cancelJob()
        }
    }

    private fun cancelJob() {
        snackbarJob?.let {
            it.cancel()
            snackbarJob = Job()
        }
    }
}