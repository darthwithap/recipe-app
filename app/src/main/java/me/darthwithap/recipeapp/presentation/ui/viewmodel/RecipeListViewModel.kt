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
import me.darthwithap.recipeapp.util.PAGE_SIZE
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
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0
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
            delay(2000)
            val result = repository.search(
                token,
                1,
                query.value
            )
            recipes.value = result
            loading.value = false
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            if ((recipeListScrollPosition + 1) >= (page.value * PAGE_SIZE)) {
                loading.value = true
                incrementPage()

                if (page.value > 1) {
                    val result = repository.search(
                        token,
                        page.value,
                        query.value
                    )
                    appendRecipes(result)
                    loading.value = false
                }
            }
        }
    }

    private fun incrementPage() {
        page.value += 1
    }

    private fun appendRecipes(newList: List<Recipe>) {
        val current = ArrayList(recipes.value)
        current.addAll(newList)
        recipes.value = current
    }

    fun onRecipeScrollPositionChanged(position: Int) {
        recipeListScrollPosition = position
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
        page.value = 1
        onRecipeScrollPositionChanged(0)
        if (selectedCategory.value?.value != query.value) clearSelectedCategory()
    }

    private fun clearSelectedCategory() {
        selectedCategory.value = null
    }
}