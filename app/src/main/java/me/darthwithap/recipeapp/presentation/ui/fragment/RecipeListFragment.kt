package me.darthwithap.recipeapp.presentation.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import me.darthwithap.recipeapp.R
import me.darthwithap.recipeapp.presentation.ui.viewmodel.RecipeListViewModel

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
                recipes.forEach {
                    Log.d(TAG, "onCreateView: ${it.title}")
                }
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recipe List",
                        style = TextStyle(
                            fontSize = TextUnit(24f, TextUnitType.Sp)
                        )
                    )
                    Spacer(modifier = Modifier.padding(top = 8.dp))
                    Button(onClick = {
                        findNavController().navigate(R.id.viewRecipe)
                    }) {
                        Text(text = "View Recipe")
                    }
                }
            }
        }
    }
}