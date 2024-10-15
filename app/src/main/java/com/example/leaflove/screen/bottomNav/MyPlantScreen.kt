package com.example.leaflove.screen.bottomNav

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
fun myPlantScreen(navHost: NavHostController){
    val image2 = painterResource(R.drawable.header)
    val image3 = painterResource(R.drawable.menumyplant)
    val image4 = painterResource(R.drawable.footermyplant)
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

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp),
        horizontalAlignment = Alignment.Start)
    {
        Spacer(modifier = Modifier.padding(60.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "My Plant",
            fontSize = 24.sp)
    }


    Column(
        modifier = Modifier
        .fillMaxSize()
        .padding(start =16.dp),
        horizontalAlignment = Alignment.Start)
    {
        Spacer(modifier = Modifier.padding(72.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Week 1",
            fontSize = 11.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start =16.dp),
        horizontalAlignment = Alignment.Start)
    {
        Spacer(modifier = Modifier.padding(100.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Temperature",
            fontSize = 16.sp)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start =35.dp),
        horizontalAlignment = Alignment.Start)
    {
        Spacer(modifier = Modifier.padding(110.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "24 C",
            fontSize = 12.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start =16.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.padding(100.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Weather",
            fontSize = 16.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start =35.dp),
        horizontalAlignment = Alignment.CenterHorizontally)
    {
        Spacer(modifier = Modifier.padding(110.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Cloudy",
            fontSize = 12.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end =16.dp),
        horizontalAlignment = Alignment.End)
    {
        Spacer(modifier = Modifier.padding(100.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "Humidity",
            fontSize = 16.sp)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end =35.dp),
        horizontalAlignment = Alignment.End)
    {
        Spacer(modifier = Modifier.padding(110.dp))
        Text(
            fontWeight = FontWeight.Bold,
            text = "100%",
            fontSize = 12.sp)
    }


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
