package com.plcoding.tracker.domain.model

sealed class MealType(val name: String) {
    object Breakfast: MealType("breakfast")
    object Lunch: MealType("lunch")
    object Dinner: MealType("dinner")
    object Snack: MealType("snack")

    companion object {
        private val map = values().associateBy(MealType::name)

        fun fromString(name: String): MealType {
            return map[name.lowercase()] ?: Breakfast
        }

        private fun values() = listOf(Breakfast, Lunch, Dinner, Snack)
    }
}
