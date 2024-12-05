package com.example.leaflove.ui.components

import CameraPermissionScreen
import androidx.compose.runtime.Composable
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
import com.example.leaflove.ui.screen.plantscreen.addmyplant
import com.example.leaflove.ui.screen.plantscreen.MyPlantDetail
import com.example.leaflove.ui.screen.testing.TestingScreen
import com.example.leaflove.ui.screen.storescreen.StoreScreen

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
            MyPlantDetail(navController)
        }
        composable(route = "camerascreen"){
            CameraPermissionScreen(navController)
        }

        composable(route = "about"){
            AboutScreen(navHost = navController)
        }

    }
}