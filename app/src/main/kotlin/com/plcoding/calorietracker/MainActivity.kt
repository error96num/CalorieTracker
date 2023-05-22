package com.plcoding.calorietracker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.plcoding.calorietracker.ui.theme.CalorieTrackerTheme
import com.plcoding.core.domain.preferences.Preferences
import com.plcoding.calorietracker.navigation.NavigationHelper
import com.plcoding.onboarding.presentation.activity.ActivityScreen
import com.plcoding.onboarding.presentation.age.AgeScreen
import com.plcoding.onboarding.presentation.gender.GenderScreen
import com.plcoding.onboarding.presentation.goal.GoalScreen
import com.plcoding.onboarding.presentation.height.HeightScreen
import com.plcoding.onboarding.presentation.nutrient_goal.NutrientGoalScreen
import com.plcoding.onboarding.presentation.weight.WeightScreen
import com.plcoding.onboarding.presentation.welcome.WelcomeScreen
import com.plcoding.tracker.presentation.search.SearchScreen
import com.plcoding.tracker.presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnboarding = preferences.loadShouldShowOnboarding()
        setContent {
            CalorieTrackerTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState,
                ) { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = if(shouldShowOnboarding) {
                            NavigationHelper.Welcome.route
                        } else NavigationHelper.TrackerOverview.route,
                        modifier = Modifier.padding(padding),
                    ) {
                        composable(NavigationHelper.Welcome.route) {
                            WelcomeScreen(onNextClick = {
                                navController.navigate(NavigationHelper.Gender.route)
                            })
                        }
                        composable(NavigationHelper.Age.route) {
                            AgeScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(NavigationHelper.Height.route)
                                }
                            )
                        }
                        composable(NavigationHelper.Gender.route) {
                            GenderScreen(onNextClick = {
                                navController.navigate(NavigationHelper.Age.route)
                            })
                        }
                        composable(NavigationHelper.Height.route) {
                            HeightScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(NavigationHelper.Weight.route)
                                },
                            )
                        }
                        composable(NavigationHelper.Weight.route) {
                            WeightScreen(
                                scaffoldState = scaffoldState,
                                onNavigate = {
                                    navController.navigate(NavigationHelper.Activity.route)
                                },
                            )
                        }
                        composable(NavigationHelper.Activity.route) {
                            ActivityScreen(onNextClick = {
                                navController.navigate(NavigationHelper.Goal.route)
                            })
                        }
                        composable(NavigationHelper.Goal.route) {
                            GoalScreen(onNavigate = {
                                navController.navigate(NavigationHelper.NutrientGoal.route)
                            })
                        }
                        composable(NavigationHelper.NutrientGoal.route) {
                            NutrientGoalScreen(
                                scaffoldState = scaffoldState,
                                onNextClick = {
                                    navController.navigate(NavigationHelper.TrackerOverview.route)
                                },
                            )
                        }
                        composable(NavigationHelper.TrackerOverview.route) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(NavigationHelper.Search.route +
                                            "/$mealName" +
                                            "/$day" +
                                            "/$month" +
                                            "/$year"
                                    )
                                }
                            )
                        }
                        composable(
                            route = NavigationHelper.Search.route +
                                    "/{${NavigationHelper.Search.mealName}}" +
                                    "/{${NavigationHelper.Search.dayOfMonth}}" +
                                    "/{${NavigationHelper.Search.month}}" +
                                    "/{${NavigationHelper.Search.year}}",
                            arguments = listOf(
                                navArgument(NavigationHelper.Search.mealName) {
                                    type = NavType.StringType
                                },
                                navArgument(NavigationHelper.Search.dayOfMonth) {
                                    type = NavType.IntType
                                },
                                navArgument(NavigationHelper.Search.month) {
                                    type = NavType.IntType
                                },
                                navArgument(NavigationHelper.Search.year) {
                                    type = NavType.IntType
                                },
                            ),
                        ) {
                            val mealName = it.arguments?.getString(NavigationHelper.Search.mealName)!!
                            val dayOfMonth = it.arguments?.getInt(NavigationHelper.Search.dayOfMonth)!!
                            val month = it.arguments?.getInt(NavigationHelper.Search.month)!!
                            val year = it.arguments?.getInt(NavigationHelper.Search.year)!!
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = mealName,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}
