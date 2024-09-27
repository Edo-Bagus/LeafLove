package com.example.leaflove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.ui.theme.Background

@Composable
fun AboutScreen(navHost: NavHostController){
    val image = painterResource(R.drawable.header)
    val image1 = painterResource(R.drawable.footerstore)
    val image2 = painterResource(R.drawable.textabout)
    val image3 = painterResource(R.drawable.text1about)
    val image4 = painterResource(R.drawable.fotoedo)
    val image5 = painterResource(R.drawable.fotomarcel)
    val image6 = painterResource(R.drawable.fotodapa)
    val image7 = painterResource(R.drawable.fotoilham)

    var username by remember { mutableStateOf("") }
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp


    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .offset(y = -columnsize)
            .zIndex(1.1f)) {
            Image(painter = image,
                contentDescription = "",
                modifier = Modifier
                    .fillMaxSize())
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .offset(y = 700.dp)
            .zIndex(1f)) {
            Image(
                painter = image1,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .height(500.dp)
                .offset(y = 70.dp)
                .padding(start = 16.dp),
            horizontalAlignment = Alignment.Start)
        {
            Spacer(modifier = Modifier.padding(30.dp))
            Text(
                fontWeight = FontWeight.Bold,
                text = "About",
                fontSize = 24.sp)
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .offset(y = 10.dp)
            .zIndex(1f)) {
            Spacer(modifier = Modifier.padding(30.dp))
            Image(
                painter = image2,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .offset(y = 100.dp)
            .zIndex(1f)
        ) {
            Spacer(modifier = Modifier.padding(30.dp))
            Image(
                painter = image3,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }














    }
}







