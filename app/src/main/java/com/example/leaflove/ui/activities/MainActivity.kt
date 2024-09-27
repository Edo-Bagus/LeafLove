package com.example.leaflove.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.BuildConfig
import com.example.leaflove.ui.compose.ARScreen
import com.example.leaflove.ui.compose.AccountScreen
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.ui.compose.StoreScreen
import com.example.leaflove.ui.compose.TransactionScreen
import com.example.leaflove.viewmodel.WeatherViewModel
import com.example.leaflove.ui.compose.loginScreen
import com.example.leaflove.ui.compose.mainScreen
import com.example.leaflove.ui.compose.myPlantScreen
import com.example.leaflove.ui.compose.registerScreen
import com.example.leaflove.ui.theme.LeafLoveTheme
import com.example.leaflove.ui.theme.rememberWindowSizeClass

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d("test", BuildConfig.WEATHER_API_KEY)

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