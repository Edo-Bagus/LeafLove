package com.example.leaflove.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.leaflove.R

@Composable
fun Homepagescreen2(){
    Box(modifier = Modifier.background(color = Color.Black)){
        Image(painter = painterResource(R.drawable.mainmenubg), contentDescription = "Bg")
    }
}