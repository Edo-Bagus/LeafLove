package com.example.leaflove.ui.screen.headerNav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R

@Composable
fun TransactionScreen(navHost: NavHostController){
    val image = painterResource(R.drawable.header)
    val image1 = painterResource(R.drawable.footerstore)
    val image2 = painterResource(R.drawable.successtransaction)
    val image3 = painterResource(R.drawable.failedtransaction)
    var username by remember { mutableStateOf("") }
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp


    Box(modifier = Modifier
        .fillMaxSize()
    ) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .offset(y = -columnsize)
            .zIndex(1.0f)) {
            Image(painter = image, contentDescription = "", modifier = Modifier
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .offset(y = (-50).dp)
                .zIndex(1f)
        ) {
            Image(
                painter = image2,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
                .offset(y = 20.dp)
                .zIndex(1f)
        ) {
            Image(
                painter = image3,
                contentDescription = "",
                modifier = Modifier.fillMaxSize()
            )
        }



    }
}







