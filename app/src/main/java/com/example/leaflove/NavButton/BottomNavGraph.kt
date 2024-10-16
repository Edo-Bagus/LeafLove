package com.example.leaflove.NavButton

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaflove.screen.Homepagescreen2
import com.example.leaflove.screen.bottomNav.HomeScreen
import com.example.leaflove.screen.bottomNav.ARScreen
import com.example.leaflove.screen.bottomNav.myPlantScreen
import com.example.leaflove.screen.headerNav.TransactionScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.WeatherViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController
) {
    val weatherViewModel: WeatherViewModel = WeatherViewModel();
    val locViewModel: LocationViewModel = LocationViewModel();
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp // Height of the screen in dp

    // Set a responsive offset, for example, 10% of the screen height
    val responsiveOffset = screenHeight * 0.1f // Adjust the factor as needed
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route,
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

        composable("homepage2", enterTransition = { slideInVertically() })
        {
            Homepagescreen2()
        }
    }


}