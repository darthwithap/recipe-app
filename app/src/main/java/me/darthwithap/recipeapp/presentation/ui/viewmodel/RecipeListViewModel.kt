package me.darthwithap.recipeapp.presentation.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.presentation.util.FoodCategory
import me.darthwithap.recipeapp.presentation.util.getFoodCategory
import me.darthwithap.recipeapp.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    //private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    private var _categoryScrollPosition: Float = 0f

    init {
        search()
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun search() {
        viewModelScope.launch {
            val result = repository.search(
                token,
                1,
                query.value
            )
            recipes.value = result
        }
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        selectedCategory.value = newCategory
        onQueryChanged(category)
    }

    fun onCategoryScrollPositionChanged(position: Float) {
        _categoryScrollPosition = position
    }
}