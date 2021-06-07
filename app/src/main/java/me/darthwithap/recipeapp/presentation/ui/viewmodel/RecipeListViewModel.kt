package me.darthwithap.recipeapp.presentation.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.presentation.util.FoodCategory
import me.darthwithap.recipeapp.presentation.util.getFoodCategory
import me.darthwithap.recipeapp.repository.RecipeRepository
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    //private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    private var _categoryScrollPosition: Float = 0f

    init {
        search()
    }

    fun onQueryChanged(query: String) {
        this.query.value = query
    }

    fun search() {
        viewModelScope.launch {
            loading.value = true
            resetSearchState()
            delay(250)
            val result = repository.search(
                token,
                1,
                query.value
            )
            recipes.value = result
            loading.value = false
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

    fun categoryScrollPosition() = _categoryScrollPosition

    private fun resetSearchState() {
        recipes.value = listOf()
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }
}