package me.darthwithap.recipeapp.presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import androidx.compose.ui.graphics.Color
import me.darthwithap.recipeapp.presentation.components.FoodCategoryChip
import me.darthwithap.recipeapp.presentation.components.recipeCard
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

                Column {
                    Surface(
                        elevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = Color.White
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val focusManager = LocalFocusManager.current
                                TextField(
                                    modifier = Modifier
                                        .fillMaxWidth(0.8f)
                                        .padding(8.dp),
                                    value = query,
                                    onValueChange = {
                                        viewModel.onQueryChanged(it)
                                    },
                                    label = {
                                        Text(text = "Search")
                                    },
                                    keyboardOptions = KeyboardOptions(
                                        keyboardType = KeyboardType.Text,
                                        imeAction = ImeAction.Search
                                    ),
                                    leadingIcon = {
                                        Icon(
                                            imageVector = Icons.Filled.Search,
                                            contentDescription = null
                                        )
                                    },
                                    keyboardActions = KeyboardActions(
                                        onSearch = {
                                            viewModel.search()
                                            focusManager.clearFocus()
                                        }
                                    ),
                                    textStyle = TextStyle(
                                        color = MaterialTheme.colors.onSurface
                                    ),
                                    colors = textFieldColors(
                                        backgroundColor = MaterialTheme.colors.surface
                                    )
                                )
                            }
                            val scrollState = rememberScrollState()
                            LazyRow(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 8.dp, bottom = 8.dp),
                                state = rememberLazyListState(),
                            ) {
                                itemsIndexed(getAllFoodCategories()) { index, category ->
                                    FoodCategoryChip(
                                        category = category.value,
                                    isSelected = category == selectedCategory,
                                        onExecuteSearch = viewModel::search,
                                    ) {
                                        viewModel.onSelectedCategoryChanged(it)
                                    }
                                }
                            }
                        }
                    }
                    LazyColumn {
                        itemsIndexed(
                            items = recipes
                        ) { index, recipe ->
                            recipeCard(recipe) {}
                        }
                    }
                }
            }
        }
    }
}