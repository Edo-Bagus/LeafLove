package com.example.leaflove.screen.storescreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R

@Composable
fun StoreScreen(navHost: NavHostController){
    val image2 = painterResource(R.drawable.header)
    val image3 = painterResource(R.drawable.gambarstore)
    val image4 = painterResource(R.drawable.footerstore)
    val image5 = painterResource(R.drawable.trendingstore)
    var username by remember { mutableStateOf("") }
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

    Box(modifier = Modifier
        .fillMaxWidth()
        .height(500.dp)
        .offset(y = 70.dp)
        .zIndex(1f)){ Image(
        painter = image5,
        contentDescription = "",
        modifier = Modifier.fillMaxSize()
    )}


    Column(
        modifier = Modifier
            .fillMaxSize()
            .height(500.dp)
            .offset(y = 20.dp)
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start)
    {
        Spacer(modifier = Modifier.padding(30.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Trendings",
            fontSize = 24.sp)
    }


}
