package me.darthwithap.recipeapp.presentation.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.presentation.util.FoodCategory
import me.darthwithap.recipeapp.presentation.util.RecipeListEvent
import me.darthwithap.recipeapp.presentation.util.RecipeListEvent.*
import me.darthwithap.recipeapp.presentation.util.getFoodCategory
import me.darthwithap.recipeapp.repository.RecipeRepository
import me.darthwithap.recipeapp.util.*
import javax.inject.Named

private const val TAG = "RecipeListViewModel"

class RecipeListViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())
    val query = mutableStateOf("")
    val selectedCategory: MutableState<FoodCategory?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)
    val page = mutableStateOf(1)
    private var recipeListScrollPosition = 0
    private var _categoryScrollPosition: Float = 0f

    init {
        restoreFromSavedState()
        if (recipeListScrollPosition != 0 && recipes.value.isNotEmpty() && query.value.isNotBlank()) {
            onTriggerEvent(RestoreEvent)
        } else onTriggerEvent(SearchEvent)
    }

    fun onTriggerEvent(event: RecipeListEvent) {
        viewModelScope.launch {
            try {
                when (event) {
                    is SearchEvent -> {
                        search()
                    }
                    is NextPageEvent -> {
                        nextPage()
                    }
                    is RestoreEvent -> {
                        restoreState()
                    }
                }
            } catch (e: Exception) {
                Log.e(TAG, "onTriggerEvent: Error occured: ${e.cause}")
            }
        }
    }

    fun onQueryChanged(q: String) {
        setQuery(q)
    }

    private suspend fun search() {
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

    private suspend fun nextPage() {
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

    private fun incrementPage() {
        setPage(page.value + 1)
    }

    private fun appendRecipes(newList: List<Recipe>) {
        val current = ArrayList(recipes.value)
        current.addAll(newList)
        recipes.value = current
    }

    fun onRecipeScrollPositionChanged(position: Int) {
        setRecipeListScrollPosition(position)
    }

    fun onSelectedCategoryChanged(category: String) {
        val newCategory = getFoodCategory(category)
        setSelectedCategory(newCategory)
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
        setSelectedCategory(null)
    }

    private fun setRecipeListScrollPosition(position: Int) {
        recipeListScrollPosition = position
        savedStateHandle.set(STATE_KEY_RECIPE_LIST_POSITION, position)
    }

    private fun setPage(p: Int) {
        page.value = p
        savedStateHandle.set(STATE_KEY_RECIPE_LIST_PAGE, p)
    }

    private fun setSelectedCategory(foodCategory: FoodCategory?) {
        selectedCategory.value = foodCategory
        savedStateHandle.set(STATE_KEY_RECIPE_LIST_SELECTED_CATEGORY, foodCategory)
    }

    private fun setQuery(q: String) {
        query.value = q
        savedStateHandle.set(STATE_KEY_RECIPE_LIST_QUERY, q)
    }

    private fun restoreFromSavedState() {
        with(savedStateHandle) {
            get<Int>(STATE_KEY_RECIPE_LIST_PAGE)?.let { setPage(it) }
            get<String>(STATE_KEY_RECIPE_LIST_QUERY)?.let { setQuery(it) }
            get<FoodCategory?>(STATE_KEY_RECIPE_LIST_SELECTED_CATEGORY)?.let {
                setSelectedCategory(
                    it
                )
            }
            get<Int>(STATE_KEY_RECIPE_LIST_POSITION)?.let { setRecipeListScrollPosition(it) }
        }
    }

    private suspend fun restoreState() {
        loading.value = true
        val results = mutableListOf<Recipe>()
        for (p in 1 until page.value) {
            val result = repository.search(
                token,
                p,
                query.value
            )
            results.addAll(result)
            if (p == page.value) {
                recipes.value = results
                loading.value = false
            }
        }
    }
}