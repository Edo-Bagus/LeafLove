package com.example.leaflove.ui.components

import CameraPermissionScreen
import CameraScreen
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.leaflove.ui.screen.account.AboutScreen
import com.example.leaflove.ui.screen.account.AccountScreen
import com.example.leaflove.ui.screen.bottomNav.ARScreen
import com.example.leaflove.ui.screen.bottomNav.HomeScreen
import com.example.leaflove.ui.screen.bottomNav.MyPlantScreen
import com.example.leaflove.ui.screen.encyclopedia.EncyclopediaDetailScreen
import com.example.leaflove.ui.screen.encyclopedia.EncyclopediaMainScreen
import com.example.leaflove.ui.screen.headerNav.TransactionScreen
import com.example.leaflove.ui.screen.plantscreen.addmyplant
import com.example.leaflove.ui.screen.plantscreen.myplantdetail
import com.example.leaflove.ui.screen.testing.TestingScreen
import com.example.leaflove.ui.screen.storescreen.StoreScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
) {
    // Define navigation graph
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route)
        {
            HomeScreen(navHost = navController)
        }

        // Augmented Reality (Camera) screen
        composable(route = BottomBarScreen.Camera.route) {
            ARScreen()
        }

        // My Plants screen
        composable(route = BottomBarScreen.MyPlant.route) {
            MyPlantScreen(navHost = navController)
        }

        composable("transaction")
        {
            StoreScreen()
        }
        composable("searchscreen")
        {
            EncyclopediaMainScreen(navHost = navController)
        }
        
        composable("detailscreen")
        {  
            EncyclopediaDetailScreen(navHost = navController)
        }

        composable("addmyplant")
        {
            addmyplant(navHost = navController)
        }
        composable("testing"){
            TestingScreen(navController)
        }

        // Account screen
        composable(route = "account"){
            AccountScreen(navHost = navController)
        }

        composable(route = "myplantdetail")
        {
            myplantdetail(navController)
        }
        composable(route = "camerascreen"){
            CameraPermissionScreen(navController)
        }

        composable(route = "about"){
            AboutScreen(navHost = navController)
        }

    }
}