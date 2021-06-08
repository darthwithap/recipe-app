package me.darthwithap.recipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import me.darthwithap.recipeapp.network.RecipeService
import me.darthwithap.recipeapp.network.model.RecipeDtoMapper
import me.darthwithap.recipeapp.repository.RecipeRepository
import me.darthwithap.recipeapp.repository.RecipeRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object RepositoryModule {
    @Singleton
    @Provides
    fun provideRecipeRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepositoryImpl(
            recipeService,
            recipeDtoMapper
        )
    }
}