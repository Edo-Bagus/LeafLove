package com.example.leaflove.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaflove.ui.screen.account.AccountScreen
import com.example.leaflove.ui.screen.bottomNav.ARScreen
import com.example.leaflove.ui.screen.bottomNav.HomeScreen
import com.example.leaflove.ui.screen.bottomNav.MyPlantScreen
import com.example.leaflove.ui.screen.encyclopedia.EncyclopediaDetailScreen
import com.example.leaflove.ui.screen.encyclopedia.EncyclopediaMainScreen
import com.example.leaflove.ui.screen.headerNav.TransactionScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel

@Composable
fun BottomNavGraph(navController: NavHostController) {
    // Initialize view models
    val weatherViewModel = WeatherViewModel()
    val locationViewModel = LocationViewModel()
    val plantViewModel = PlantViewModel()

    // Get screen height for potential responsive design (currently unused)
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val responsiveOffset = screenHeightDp * 0.1f

    // Define navigation graph
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        // Home screen
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(
                navHost = navController,
                weatherViewModel = weatherViewModel,
                locationViewModel = locationViewModel
            )
        }

        // Augmented Reality (Camera) screen
        composable(route = BottomBarScreen.Camera.route) {
            ARScreen()
        }

        // My Plants screen
        composable(route = BottomBarScreen.MyPlant.route) {
            MyPlantScreen(navHost = navController)
        }

        // Transaction screen
        composable(route = "transaction") {
            TransactionScreen(navHost = navController)
        }

        // Encyclopedia main screen
        composable(route = "searchscreen") {
            EncyclopediaMainScreen(navHost = navController, viewModel = plantViewModel)
        }

        // Encyclopedia detail screen
        composable(route = "detailscreen") {
            EncyclopediaDetailScreen(
                navHost = navController,
                plantDetailEntity = plantViewModel.plantDetail.value
            )
        }

        // Account screen
        composable(route = "account"){
            AccountScreen(navHost = navController)
        }
    }
}
