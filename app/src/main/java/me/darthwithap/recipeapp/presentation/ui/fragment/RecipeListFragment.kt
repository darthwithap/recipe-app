package me.darthwithap.recipeapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.recipeapp.presentation.ui.viewmodel.RecipeListViewModel
import androidx.compose.material.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.presentation.components.*
import me.darthwithap.recipeapp.presentation.util.getAllFoodCategories

private const val TAG = "RecipeListFragment"

@ExperimentalUnitApi
@AndroidEntryPoint
class RecipeListFragment : Fragment() {
    private val viewModel: RecipeListViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val recipes = viewModel.recipes.value
                val query = viewModel.query.value
                val selectedCategory = viewModel.selectedCategory.value
                val loading = viewModel.loading.value

                Column {
                    SearchAppBar(
                        query = query,
                        onQueryChanged = viewModel::onQueryChanged,
                        onExecuteSearch = viewModel::search,
                        categoryScrollPosition = viewModel.categoryScrollPosition(),
                        selectedCategory = selectedCategory,
                        onSelectedCategoryChanged = viewModel::onSelectedCategoryChanged,
                        onCategoryScrollPositionChanged = viewModel::onCategoryScrollPositionChanged
                    )

                    LazyColumn {
                        repeat(10) {
                            item {
                                ShimmerRecipeCardAnimation()
                            }
                        }
                    }

//                    Box(
//                        modifier = Modifier.fillMaxSize()
//                    ) {
//                        LazyColumn {
//                            itemsIndexed(
//                                items = recipes
//                            ) { index, recipe ->
//                                recipeCard(recipe) {}
//                            }
//                        }
//
//                        CircularIndeterminateProgressBar(loading, 0.3f)
//                    }
                }
            }
        }
    }
}
