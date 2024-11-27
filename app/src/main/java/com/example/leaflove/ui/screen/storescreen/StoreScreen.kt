package com.example.leaflove.ui.screen.storescreen

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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.leaflove.R
import com.example.leaflove.ui.components.BottomNavGraph
import com.example.leaflove.ui.components.MyPlantGrid
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.ui.components.Store
import com.example.leaflove.ui.components.StoreGrid
import com.example.leaflove.ui.components.Header
import com.example.leaflove.ui.screen.BottomBar
import com.example.leaflove.ui.screen.MainScreen
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel

@Composable
fun StoreScreen(){
    val image3 = painterResource(R.drawable.menumyplant)
    val image4 = painterResource(R.drawable.footermyplant)
    var username by remember { mutableStateOf("") }
    val stores = listOf(
        Store("Aloe Vera", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Cactus", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Monstera", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Snake Plant", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Lidah Buaya", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Sawi", "Rp50.000", R.drawable.contoh_tanaman),
        Store("Sayur kol", "Rp50.000", R.drawable.contoh_tanaman),
        Store("wortel", "Rp50.000", R.drawable.contoh_tanaman)
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
                    text = "Store",
                    fontWeight = FontWeight.Bold,
                    color = BasicGreen,
                    fontSize = 32.sp,
                    modifier = Modifier.offset(x = screenWidth * 0.1f)

                )

            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(screenHeight * 0.7f)
                .offset(y = screenHeight * 0.2f)
                .background(Color.White)
                .zIndex(1f)
        )
        {
            StoreGrid(stores = stores, screenHeight, screenWidth)
        }
    }
}


@Composable
@Preview
fun BottomNavPreview() {
    StoreScreen()
}
