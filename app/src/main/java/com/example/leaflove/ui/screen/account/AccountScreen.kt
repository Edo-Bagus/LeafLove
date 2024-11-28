package com.example.leaflove.ui.screen.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun AccountScreen(navHost: NavHostController){
    var username by remember { mutableStateOf("") }
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.4f) // Mengisi 40% bagian atas layar
                    .fillMaxWidth()
                    .background(BasicGreen),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp) // Menambahkan padding agar kotak tidak menempel ke sisi layar
                        .align(Alignment.CenterStart), // Menempatkan Row di sisi kiri tengah
                    verticalAlignment = Alignment.CenterVertically // Menyelaraskan elemen secara vertikal
                ){
                    Box(
                        modifier = Modifier
                            .size(100.dp) // Ukuran kotak
                            .background(Color.White) // Warna kotak
                    )

                    Spacer(modifier = Modifier.width(16.dp)) // Spasi antara kotak dan teks

                    Column(
                        horizontalAlignment = Alignment.Start // Menyelaraskan teks di tengah kolom
                    ) {
                        Text(
                            text = "Bapak Budi",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White,
                            textAlign =TextAlign.Left
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Plant 5",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color.White,
                            textAlign = TextAlign.Left
                        )
                    }
                }
            }
        }


        Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(20.dp, 350.dp, 40.dp, 0.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(
                    text = "Log Out",
                    color = Color.Black
                )
            }
        }
    }








