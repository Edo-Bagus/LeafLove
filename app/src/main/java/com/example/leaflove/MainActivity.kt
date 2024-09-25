package com.example.leaflove

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.ui.theme.LeafLoveTheme
import com.example.leaflove.ui.theme.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val window = rememberWindowSizeClass()
            LeafLoveTheme (window){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LeafLove()
                }
            }
        }
    }
}
@Composable
fun LeafLove(){
//    val context = LocalContext.current
//    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//
//    // Check if the user is already logged in
//    var isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }
//
//    // Display either Login or Main screen based on the login state
//    if (isLoggedIn) {
    val weatherViewModel: WeatherViewModel = WeatherViewModel();
    val locViewModel: LocationViewModel = LocationViewModel();
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "loginscreen") {
        composable("loginscreen") {
            loginScreen(navController)
        }
        composable("signupscreen"){
            registerScreen(navHost = navController)
        }
        composable("mainscreen"){
            mainScreen(navHost = navController, weatherViewModel, locViewModel)
        }
        composable("myplantscreen"){
            myPlantScreen(navHost = navController)
        }
        composable("StoreScreen"){
            StoreScreen(navHost = navController)
        }
        composable("ARScreen"){
            ARScreen(navHost = navController)
        }
        composable("TransactionScreen"){
            TransactionScreen(navHost = navController)
        }
        composable("AccountScreen"){
            AccountScreen(navHost = navController)
        }
    }
    }


//}