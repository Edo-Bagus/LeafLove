package com.example.leaflove.ui.components

import com.example.leaflove.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
    val icon_focused: Int
) {

    // for home
    object Home: BottomBarScreen(
        route = "home",
        title = "Home",
        icon = R.drawable.baseline_home_24_default,
        icon_focused = R.drawable.baseline_home_24_selected
    )

    // for report
    object Camera: BottomBarScreen(
        route = "camera",
        title = "Camera",
        icon = R.drawable.baseline_photo_camera_24_default,
        icon_focused = R.drawable.baseline_camera_alt_24_selected
    )

    // for report
    object MyPlant: BottomBarScreen(
        route = "my_plant",
        title = "My Plant",
        icon = R.drawable.baseline_forest_24_default,
        icon_focused = R.drawable.baseline_forest_24_selected
    )

}