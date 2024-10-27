package com.example.leaflove.ui.screen

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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.leaflove.ui.components.header
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel

@Composable
fun MainScreen(authViewModel: AuthViewModel) {
    val navController = rememberNavController()
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    Box(modifier = Modifier.fillMaxSize().statusBarsPadding()) {

        // Content (bottom of the hierarchy)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(0f)
        ) {
            BottomNavGraph(navController)
        }

        header(
            navHost = navController,
            modifier = Modifier
                .zIndex(2f)
                .fillMaxWidth()
                .statusBarsPadding(),
            screenWidth = screenWidth,
            screenHeight = screenHeight
        )

        // BottomBar
        BottomBar(
            navController = navController,
            modifier = Modifier
                .zIndex(2f)
                .align(Alignment.BottomCenter),
            screenheight = screenHeight
        )
    }
}

@Composable
fun BottomBar(navController: NavHostController, modifier: Modifier = Modifier, screenheight:Dp) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Camera,
        BottomBarScreen.MyPlant
    )

    val navStackBackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navStackBackEntry?.destination

    Row(
        modifier = modifier
//            .padding(10.dp)
            .background(Color.White)
            .fillMaxWidth()
            .height(screenheight*0.1f), // Sesuaikan tinggi dari BottomBar
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val background =
        if (selected) BasicGreen else Color.Transparent

    val contentColor =
        if (selected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .height(40.dp)
            .clip(CircleShape)
            .background(background)
            .clickable(onClick = {
                navController.navigate(screen.route) {
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            })
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                painter = painterResource(id = if (selected) screen.icon_focused else screen.icon),
                contentDescription = "icon",
                tint = contentColor
            )
            AnimatedVisibility(visible = selected) {
                Text(
                    text = screen.title,
                    color = contentColor
                )
            }
        }
    }
}


@Composable
@Preview
fun BottomNavPreview() {
    MainScreen(authViewModel = AuthViewModel())
}
