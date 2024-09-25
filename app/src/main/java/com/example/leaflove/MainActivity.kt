package com.example.leaflove

import android.os.Bundle
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
    }


}