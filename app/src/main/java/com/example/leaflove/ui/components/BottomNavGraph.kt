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
import com.example.leaflove.ui.screen.plantscreen.addmyplant
import com.example.leaflove.ui.screen.testing.TestingScreen
import com.example.leaflove.ui.screen.storescreen.StoreScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    weatherViewModel: WeatherViewModel,
    locViewModel: LocationViewModel,
    plantViewModel: PlantViewModel
) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // Height of the screen in dp

    // Get screen height for potential responsive design (currently unused)
    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val responsiveOffset = screenHeightDp * 0.1f

    // Define navigation graph
    NavHost(
        navController = navController,
        startDestination = "addmyplant"
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen(navHost = navController, weatherViewModel, locViewModel)
        }

        // Augmented Reality (Camera) screen
        composable(route = BottomBarScreen.Camera.route) {
            ARScreen(plantViewModel)
        }

        // My Plants screen
        composable(route = BottomBarScreen.MyPlant.route) {
            MyPlantScreen(navHost = navController, weatherViewModel, locViewModel)
        }

        composable("transaction")
        {
            StoreScreen()
        }
        composable("searchscreen")
        {
            EncyclopediaMainScreen(navHost = navController, viewModel = plantViewModel)
        }
        
        composable("detailscreen")
        {  
            EncyclopediaDetailScreen(navHost = navController, plantViewModel)
        }

        composable("addmyplant")
        {
            addmyplant(navHost = navController)
        }

        // Account screen
        composable(route = "account"){
            AccountScreen(navHost = navController)
        }
    }
}