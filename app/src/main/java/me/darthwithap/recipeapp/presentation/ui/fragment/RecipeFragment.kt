package me.darthwithap.recipeapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.recipeapp.presentation.components.CircularIndeterminateProgressBar
import me.darthwithap.recipeapp.presentation.components.DefaultSnackbar
import me.darthwithap.recipeapp.presentation.components.RecipeView
import me.darthwithap.recipeapp.presentation.components.util.ShimmerRecipeAnimation
import me.darthwithap.recipeapp.presentation.components.util.SnackbarController
import me.darthwithap.recipeapp.presentation.theme.AppTheme
import me.darthwithap.recipeapp.presentation.ui.BaseApplication
import me.darthwithap.recipeapp.presentation.ui.viewmodel.RecipeViewModel
import me.darthwithap.recipeapp.presentation.util.RecipeEvent
import me.darthwithap.recipeapp.presentation.util.RecipeEvent.*
import me.darthwithap.recipeapp.util.RECIPE_VIEW_HEIGHT
import javax.inject.Inject

@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeFragment : Fragment() {
    private var isDark: Boolean = false
    private val snackbarController = SnackbarController(lifecycleScope)
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getInt("recipeId")?.let {
            viewModel.onTriggerEvent(GetRecipeEvent(it))
        }
        arguments?.getBoolean("isDark")?.let {
            isDark = it
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val loading = viewModel.loading.value
                val scaffoldState = rememberScaffoldState()
                AppTheme(isDark = isDark, loading, scaffoldState, Alignment.BottomCenter) {
                    val recipe = viewModel.recipe.value

                    Scaffold(
                        scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colors.background)
                        ) {
                            if (loading && recipe == null) {
                                ShimmerRecipeAnimation(imageHeight = RECIPE_VIEW_HEIGHT)
                            } else {
                                recipe?.let {
                                    if (it.id == 1) {
                                        snackbarController.showSnackbar(
                                            scaffoldState, "RECIPE WITH ID 1", "ERROR"
                                        )
                                    } else {
                                        RecipeView(recipe)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}