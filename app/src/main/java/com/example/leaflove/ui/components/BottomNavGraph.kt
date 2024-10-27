package com.example.leaflove.ui.components

import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaflove.ui.screen.bottomNav.HomeScreen
import com.example.leaflove.ui.screen.bottomNav.ARScreen
import com.example.leaflove.ui.screen.bottomNav.myPlantScreen
import com.example.leaflove.ui.screen.encyclopedia.EncyclopediaMainScreen
import com.example.leaflove.ui.screen.headerNav.TransactionScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    val weatherViewModel: WeatherViewModel = WeatherViewModel();
    val locViewModel: LocationViewModel = LocationViewModel();
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // Height of the screen in dp
    val plant = PlantViewModel();

    // Set a responsive offset, for example, 10% of the screen height
    val responsiveOffset = screenHeight * 0.1f // Adjust the factor as needed
    NavHost(
        navController = navController,
        startDestination = "searchscreen",
//        modifier = Modifier
//            .fillMaxSize()
//            .offset(y = -responsiveOffset)
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen(navHost = navController, weatherViewModel, locViewModel)
        }
        composable(route = BottomBarScreen.Camera.route)
        {
            ARScreen(navHost = navController)
        }
        composable(route = BottomBarScreen.MyPlant.route)
        {
            myPlantScreen(navHost = navController)
        }

        composable("transaction")
        {
            TransactionScreen(navHost = navController)
        }
        composable("searchscreen")
        {
            EncyclopediaMainScreen(navHost = navController, viewModel = plant)
        }
    }


}