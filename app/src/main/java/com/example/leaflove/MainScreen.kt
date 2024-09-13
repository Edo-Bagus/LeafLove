package com.example.leaflove

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController

@Composable
fun mainScreen(navHost: NavHostController){
    val image = painterResource(R.drawable.mainmenu)
    val image2 = painterResource(R.drawable.header)
    val image3 = painterResource(R.drawable.mainmenu2)
    val image4 = painterResource(R.drawable.footer)
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp

    Box(modifier = Modifier
        .fillMaxWidth()
        .offset(y = -columnsize)
        .zIndex(1.0f)){
        Image(painter = image2, contentDescription = "", modifier = Modifier
            .fillMaxSize())
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(714.dp)
        .offset(x = 0.dp, y = -offsetmainmenu)
        .zIndex(0.9f)){
        Image(painter = image,
            contentDescription = "",
            modifier = Modifier.fillMaxSize())
    }
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .offset(y = 410.dp)
        .zIndex(1f)){ Image(
        painter = image3,
        contentDescription = "",
            modifier = Modifier.fillMaxSize()
    )}

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .offset(y = 700.dp)
        .zIndex(1f)){ Image(
        painter = image4,
        contentDescription = "",
        modifier = Modifier.fillMaxSize()
    )}

//    Column {
//        Row{
//            Image(painter = image2, contentDescription = "", modifier = Modifier.fillMaxSize())
//        }
//        Row{
//            Box(modifier = Modifier
//                .fillMaxWidth()
//                .height(410.dp)
//                .offset(x = 0.dp)){
//
//                Image(painter = image, contentDescription = "", modifier = Modifier.fillMaxSize())
//            }
//        }
//        Row{
//
//        }
    }
