package com.plcoding.tracker.domain.model

sealed class MealType(val name: String) {
    object Breakfast: MealType("breakfast")
    object Lunch: MealType("lunch")
    object Dinner: MealType("dinner")
    object Snack: MealType("snack")

    companion object {
        private val map by lazy { listOf(Breakfast, Lunch, Dinner, Snack).associateBy(MealType::name) }

        fun fromString(name: String): MealType {
            return map[name.lowercase()] ?: Breakfast
        }
    }
}
