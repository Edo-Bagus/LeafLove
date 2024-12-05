package com.example.leaflove.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.ui.components.BottomBarScreen
import com.example.leaflove.ui.components.BottomNavGraph
import com.example.leaflove.ui.components.Header
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthState
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import org.koin.compose.koinInject

@Composable
fun MainScreen(loginScreenNavHostController: NavHostController) {
    val authViewModel = koinInject<AuthViewModel>()

    // Create NavController for navigation
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current
    val context = LocalContext.current

    // Screen dimensions for responsive design
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val authState = authViewModel.authState.value

    LaunchedEffect(authState, authViewModel.userData.value) {
        when (authState) {
            is AuthState.Unauthenticated -> {
                if (authViewModel.userData.value == null) {
                    loginScreenNavHostController.navigate("loginscreen")
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    authState.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> Unit
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {
        // Main content: Navigation graph
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f) // Base layer for the navigation content
        ) {
            BottomNavGraph(navController = navController)
        }

        // Header component (optional, above navigation content)
        Header(
            navController = navController,
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .statusBarsPadding(),
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

        // Bottom navigation bar
        BottomBar(
            navController = navController,
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter),
            screenHeight = screenHeight
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    screenHeight: Dp
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Camera,
        BottomBarScreen.MyPlant
    )

    // Observe current navigation destination
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination

    Row(
        modifier = modifier
            .background(Color.White)
            .fillMaxWidth()
            .height(screenHeight * 0.1f), // Dynamic height adjustment
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Add items dynamically for each screen
        screens.forEach { screen ->
            BottomBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun BottomBarItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    // Check if the current screen is selected
    val isSelected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val backgroundColor = if (isSelected) BasicGreen else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable {
                // Navigate to the selected screen
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true // Avoid multiple instances of the same screen
                }
            }
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // Display icon
            Icon(
                painter = painterResource(id = if (isSelected) screen.iconFocused else screen.icon),
                contentDescription = "${screen.title} icon",
                tint = contentColor
            )
            // Display text only if selected
            AnimatedVisibility(visible = isSelected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}
