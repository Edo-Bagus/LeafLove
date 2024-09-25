package com.example.leaflove

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.ui.theme.LeafLoveTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LeafLoveTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LeafLove()
                }
            }
        }
    }
}
@Composable
fun LeafLove(){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "AccountScreen") {
        composable("loginscreen") {
            loginScreen(navController)
        }
        composable("signupscreen"){
            registerScreen(navHost = navController)
        }
        composable("mainscreen"){
            mainScreen(navHost = navController)
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