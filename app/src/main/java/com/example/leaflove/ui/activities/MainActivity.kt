package com.example.leaflove.ui.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.BuildConfig
import com.example.leaflove.ui.screen.MainScreen
import com.example.leaflove.ui.screen.account.AccountScreen
import com.example.leaflove.ui.screen.bottomNav.myPlantScreen
import com.example.leaflove.ui.screen.headerNav.TransactionScreen
import com.example.leaflove.ui.screen.loginscreen.loginScreen
import com.example.leaflove.ui.screen.loginscreen.registerScreen
import com.example.leaflove.ui.screen.storescreen.StoreScreen
import com.example.leaflove.ui.theme.LeafLoveTheme
import com.example.leaflove.ui.theme.rememberWindowSizeClass
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authviewmodel = AuthViewModel();
        Log.d("test", BuildConfig.WEATHER_API_KEY)

        setContent {
            val window = rememberWindowSizeClass()
            LeafLoveTheme (window){
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LeafLove(authviewmodel)
                }
            }
        }
    }


}
@Composable
//fun LeafLove(){
//    val context = LocalContext.current
//    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
//
//    // Check if the user is already logged in
//    var isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }
//
//    // Display either Login or Main screen based on the login state
//    if (isLoggedIn) {
//    val weatherViewModel: WeatherViewModel = WeatherViewModel();
//    val locViewModel: LocationViewModel = LocationViewModel();
//    val navController = rememberNavController()
//
//    NavHost(navController = navController, startDestination = "mainscreen") {
//        composable("loginscreen") {
//            loginScreen(navController)
//        }
//        composable("signupscreen"){
//            registerScreen(navHost = navController)
//        }
//        composable("mainscreen"){
//            MainScreen()
//        }
//        composable("StoreScreen"){
//            StoreScreen(navHost = navController)
//        }
//        composable("TransactionScreen"){
//            TransactionScreen(navHost = navController)
//        }
//        composable("AccountScreen"){
//            AccountScreen(navHost = navController)
//        }
//        }
//    }
//}

fun LeafLove(authViewModel: AuthViewModel) {
    val appAuthViewModel = authViewModel;
    val plantViewModel = PlantViewModel();
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    // Check if the user is already logged in
    var isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }
    val navController = rememberNavController()

    // Display either Login or Main screen based on the login state
//    NavHost(navController = navController, startDestination = if (isLoggedIn) "mainscreen" else "loginscreen") {


        NavHost(navController = navController, startDestination = "mainscreen") {

        composable("loginscreen") {
            loginScreen(navController, appAuthViewModel)
        }
        composable("signupscreen") {
            registerScreen(navHost = navController, appAuthViewModel)
        }
        composable("mainscreen") {
            MainScreen(appAuthViewModel) // No navController needed to be passed here
        }
            composable("testing") {
                Testing(navController, plantViewModel) // No navController needed to be passed here
            }

//        composable("transaction") {
//            TransactionScreen(navHost = navController)
//        }
    }
}




//}