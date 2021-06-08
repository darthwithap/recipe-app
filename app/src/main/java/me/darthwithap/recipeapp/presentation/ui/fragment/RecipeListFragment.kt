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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.google.android.material.bottomappbar.BottomAppBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.presentation.components.CircularIndeterminateProgressBar
import me.darthwithap.recipeapp.presentation.components.DefaultSnackbar
import me.darthwithap.recipeapp.presentation.components.SearchAppBar
import me.darthwithap.recipeapp.presentation.components.recipeCard
import me.darthwithap.recipeapp.presentation.components.util.ShimmerRecipeCardAnimation
import me.darthwithap.recipeapp.presentation.components.util.SnackbarController
import me.darthwithap.recipeapp.presentation.theme.AppTheme
import me.darthwithap.recipeapp.presentation.ui.BaseApplication
import me.darthwithap.recipeapp.presentation.ui.viewmodel.RecipeListViewModel
import me.darthwithap.recipeapp.util.RECIPE_CARD_HEIGHT
import javax.inject.Inject


@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    @Inject
    lateinit var application: BaseApplication
    private val viewModel: RecipeListViewModel by viewModels()
    private val snackbarController = SnackbarController(lifecycleScope)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AppTheme(darkTheme = application.isDarkTheme.value) {
                    val recipes = viewModel.recipes.value
                    val query = viewModel.query.value
                    val selectedCategory = viewModel.selectedCategory.value
                    val loading = viewModel.loading.value
                    val isDark = application.isDarkTheme.value
                    val scaffoldState = rememberScaffoldState()

                    Scaffold(
                        topBar = {
                            SearchAppBar(
                                query = query,
                                onQueryChanged = viewModel::onQueryChanged,
                                onExecuteSearch = {
                                    if (viewModel.selectedCategory.value?.value == ("Milk")) {
                                        snackbarController.getScope().launch {
                                            snackbarController.showSnackbar(
                                                scaffoldState, "Error on MILK recipes", "ERROR"
                                            )
                                        }
                                    } else viewModel.search()
                                },
                                categoryScrollPosition = viewModel.categoryScrollPosition(),
                                selectedCategory = selectedCategory,
                                onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                                onCategoryScrollPositionChanged = viewModel::onCategoryScrollPositionChanged,
                                toggleThemeIcon = if (isDark) Icons.Filled.WbSunny else Icons.Filled.NightlightRound
                            ) {
                                application.toggleTheme()
                            }
                        }, scaffoldState = scaffoldState,
                        snackbarHost = { scaffoldState.snackbarHostState }
                    ) {
                        Column(
                            modifier = Modifier.background(MaterialTheme.colors.background)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize()
                            ) {
                                if (loading && recipes.isEmpty()) ShimmerRecipeCardAnimation(
                                    RECIPE_CARD_HEIGHT
                                )

                                LazyColumn {
                                    itemsIndexed(
                                        items = recipes
                                    ) { index, recipe ->
                                        recipeCard(recipe) {}
                                    }
                                }
                                CircularIndeterminateProgressBar(loading, 0.3f)
                                DefaultSnackbar(
                                    snackbarHostState = scaffoldState.snackbarHostState,
                                    modifier = Modifier.align(Alignment.BottomCenter)
                                ) {
                                    scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

