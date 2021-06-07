package me.darthwithap.recipeapp.presentation.util

import me.darthwithap.recipeapp.presentation.util.FoodCategory.*

enum class FoodCategory(val value: String) {
    CHICKEN("Chicken"),
    SOUP("Soup"),
    DESSERT("Dessert"),
    VEGETARIAN("Vegetarian"),
    PIZZA("Pizza"),
    DONUT("Donut"),
    MILK("Milk"),
    BEEF("Beef"),
    KALE("Kale"),
    PASTA("Pasta"),
    SWEET("Sweet"),
}

fun getAllFoodCategories(): List<FoodCategory> {
    return listOf(
        CHICKEN,
        SOUP,
        DESSERT,
        VEGETARIAN,
        PIZZA,
        DONUT,
        MILK,
        BEEF,
        KALE,
        PASTA,
        SWEET,
    )
}

fun getFoodCategory(value: String): FoodCategory? {
    val map = FoodCategory.values()
        .associateBy(FoodCategory::value)
    return map[value]
}