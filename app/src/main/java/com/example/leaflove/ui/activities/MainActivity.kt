package com.example.leaflove.ui.activities

import android.content.Context
import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
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
import com.example.leaflove.data.dao.PlantDetailDao_Impl
import com.example.leaflove.data.dao.PlantSpeciesDao_Impl
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.ui.screen.MainScreen
import com.example.leaflove.ui.screen.account.AccountScreen
import com.example.leaflove.ui.screen.bottomNav.MyPlantScreen
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
import org.koin.androidx.compose.koinViewModel
import org.koin.core.annotation.KoinInternalApi
import org.koin.viewmodel.resolveViewModel


class MainActivity : ComponentActivity() {
    private val requestCameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                // Handle permission denied (show a dialog or return)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
        enableEdgeToEdge()
        val authviewmodel = AuthViewModel();

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
@OptIn(KoinInternalApi::class)
@Composable
fun LeafLove(authViewModel: AuthViewModel) {
    val appAuthViewModel = koinViewModel<AuthViewModel>();
    val plantViewModel:PlantViewModel = koinViewModel();
    val context = LocalContext.current
    val sharedPref = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
    // Check if the user is already logged in
    var isLoggedIn by remember { mutableStateOf(sharedPref.getBoolean("isLoggedIn", false)) }
    val navController = rememberNavController()

    // Display either Login or Main screen based on the login state
//    NavHost(navController = navController, startDestination = if (isLoggedIn) "mainscreen" else "loginscreen") {


        NavHost(navController = navController, startDestination = "loginscreen") {

        composable("loginscreen") {
            loginScreen(navController, appAuthViewModel)
        }
        composable("signupscreen") {
            registerScreen(navHost = navController, appAuthViewModel)
        }
        composable("mainscreen") {
            MainScreen(appAuthViewModel) // No navController needed to be passed here
        }

//        composable("transaction") {
//            TransactionScreen(navHost = navController)
//        }
    }
}




//}