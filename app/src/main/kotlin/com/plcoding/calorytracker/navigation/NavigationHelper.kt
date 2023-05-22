package com.plcoding.calorytracker.navigation

object NavigationHelper {

    interface AddressableScreen {
        val route: String
    }

    object Welcome: AddressableScreen {
        override val route = "welcome"
    }
    object Age: AddressableScreen {
        override val route = "age"
    }
    object Gender: AddressableScreen {
        override val route: String = "gender"
    }
    object Height: AddressableScreen {
        override val route: String = "height"
    }
    object Weight: AddressableScreen {
        override val route: String = "weight"
    }
    object NutrientGoal: AddressableScreen {
        override val route: String = "nutrient_goal"
    }
    object Activity: AddressableScreen {
        override val route: String = "activity"
    }
    object Goal: AddressableScreen {
        override val route: String = "goal"
    }

    object TrackerOverview: AddressableScreen {
        override val route: String = "tracker_overview"
    }
    object Search: AddressableScreen {
        override val route: String = "search"
        const val mealName: String = "mealName"
        const val dayOfMonth: String = "dayOfMonth"
        const val month: String = "month"
        const val year: String = "year"
    }
}
