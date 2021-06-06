package me.darthwithap.recipeapp.presentation.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import me.darthwithap.recipeapp.domain.model.Recipe
import me.darthwithap.recipeapp.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Named

class RecipeListViewModel @ViewModelInject constructor(
    //private val savedStateHandle: SavedStateHandle,
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String
) : ViewModel() {
    val recipes: MutableState<List<Recipe>> = mutableStateOf(listOf())

    init {
        viewModelScope.launch {
            val result = repository.search(
                token,
                1,
                "chicken"
            )
            recipes.value = result
        }
    }
}