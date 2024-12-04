package com.example.leaflove.ui.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.ui.screen.MainScreen
import com.example.leaflove.ui.screen.loginscreen.LoginScreen
import com.example.leaflove.ui.screen.loginscreen.RegisterScreen
import com.example.leaflove.ui.theme.LeafLoveTheme
import com.example.leaflove.ui.theme.rememberWindowSizeClass
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.viewmodel.MyPlantViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.example.leaflove.viewmodel.WeatherViewModel
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinInternalApi


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authviewmodel = AuthViewModel();

        setContent {
            val window = rememberWindowSizeClass()
            LeafLoveTheme (window) {
                KoinAndroidContext {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        LeafLove(authviewmodel)
                    }
                }
            }
        }
    }


}
@OptIn(KoinInternalApi::class)
@Composable
fun LeafLove(authViewModel: AuthViewModel) {

    val weatherViewModel: WeatherViewModel = koinViewModel();
    val locViewModel: LocationViewModel = koinViewModel();
    val appAuthViewModel = koinViewModel<AuthViewModel>();
    val plantViewModel:PlantViewModel = koinViewModel();
    val myPlantViewModel:MyPlantViewModel = koinViewModel()
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    // Check if the user is already logged in
    val navController = rememberNavController()

    // Display either Login or Main screen based on the login state
//    NavHost(navController = navController, startDestination = if (isLoggedIn) "mainscreen" else "loginscreen") {



    NavHost(navController = navController, startDestination = "loginscreen") {

        composable("loginscreen") {
            LoginScreen(navController)
        }
        composable("signupscreen") {
            RegisterScreen(navHost = navController)
        }
        composable("mainscreen") {
            MainScreen(navController) // No navController needed to be passed here
        }

//        composable("transaction") {
//            TransactionScreen(navHost = navController)
//        }
    }
}




//}