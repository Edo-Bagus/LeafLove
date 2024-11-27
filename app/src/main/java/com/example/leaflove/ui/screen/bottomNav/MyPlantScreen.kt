package com.example.leaflove.ui.screen.bottomNav

import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.components.MyPlantGrid
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun MyPlantScreen(navHost: NavHostController){
    val image3 = painterResource(R.drawable.menumyplant)
    val image4 = painterResource(R.drawable.footermyplant)
    var username by remember { mutableStateOf("") }
    val plants = listOf(
        Plant("Aloe Vera", "Healthy", R.drawable.contoh_tanaman),
        Plant("Cactus", "Needs Water", R.drawable.contoh_tanaman),
        Plant("Monstera", "Growing", R.drawable.contoh_tanaman),
        Plant("Snake Plant", "Thriving", R.drawable.contoh_tanaman),
        Plant("Snake Plant", "Thriving", R.drawable.contoh_tanaman)
    )

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.3f)
                .offset(y = screenHeight * 0.1f)
                .zIndex(1f)
        ) {
            Column {
                Text(
                    text = "Hai",
                    color = BasicGreen,
                    fontSize = 32.sp,
                    modifier = Modifier.offset(x = screenWidth * 0.1f)
                )
                Text(
                    text = "Week 1",
                    color = Color.LightGray,
                    fontSize = 18.sp,
                    modifier = Modifier.offset(x = screenWidth * 0.1f)
                )

                Row {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Temperature")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Weather")
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = "Humidity")
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.7f)
                .offset(y = screenHeight * 0.3f)
                .background(Color.White)
                .zIndex(1f)
        )
        {
            MyPlantGrid(plants = plants, screenHeight, screenWidth)
        }
    }
}