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
import me.darthwithap.recipeapp.presentation.util.RecipeEvent
import me.darthwithap.recipeapp.presentation.util.RecipeEvent.*
import me.darthwithap.recipeapp.repository.RecipeRepository
import me.darthwithap.recipeapp.util.STATE_KEY_RECIPE_ID
import java.lang.Exception
import javax.inject.Named

private const val TAG = "RecipeViewModel"

class RecipeViewModel
@ViewModelInject
constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    val recipe: MutableState<Recipe?> = mutableStateOf(null)
    val loading: MutableState<Boolean> = mutableStateOf(false)

    init {
        //restore from process death
        savedStateHandle.get<Int>(STATE_KEY_RECIPE_ID)?.let {
            onTriggerEvent(GetRecipeEvent(it))
        }

    }

    fun onTriggerEvent(recipeEvent: RecipeEvent) {
        viewModelScope.launch {
            try {
                when (recipeEvent) {
                    is GetRecipeEvent -> {
                        if (recipe.value == null) {
                            getRecipe(recipeEvent.id)
                        }
                    }
                }
            } catch (e: Exception) {
                Log.d(TAG, "onTriggerEvent: Error: ${e.cause}")
            }
        }
    }

    private suspend fun getRecipe(recipeId: Int) {
        loading.value = true
        delay(100)
        val result = repository.getRecipe(
            token,
            recipeId
        )
        recipe.value = result
        savedStateHandle.set(STATE_KEY_RECIPE_ID, recipeId)
        loading.value = false
    }

}