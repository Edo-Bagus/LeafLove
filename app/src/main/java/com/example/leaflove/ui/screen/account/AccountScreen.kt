package com.example.leaflove.ui.screen.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel
import org.koin.compose.koinInject

@Composable
fun AccountScreen(navHost: NavHostController){
    val image1 = painterResource(R.drawable.ilham)
    var username by remember { mutableStateOf("") }
    val columnsize = 420.dp
    val offsetmainmenu = 100.dp
    val authViewModel = koinInject<AuthViewModel>()
    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )

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
                    .height(screenHeight * 0.25f)
                    .fillMaxWidth()
                    .background(BasicGreen)
            ) {
                Column(
                    modifier =  Modifier
                        .padding(screenWidth * 0.05f, screenHeight * 0.08f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = authViewModel.userData.value?.username.toString(),
                        fontFamily = customfont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White,
                        textAlign =TextAlign.Left,
                    )
                    Spacer(modifier = Modifier.weight(0.3f))
                    Text(
                        text = "Plant : " + (authViewModel.userData.value?.my_plants?.size ?: 0),
                        fontWeight = FontWeight.Bold,
                        fontFamily = customfont,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Left
                    )
                    Spacer(modifier = Modifier.weight(2f))
                }
            }
            Spacer(modifier = Modifier.weight(0.05f))
            Box(
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.1f)
                    .clickable { navHost.navigate("about") }
            ){
                Text(
                    text = "About us",
                    fontFamily = customfont,
                    fontSize = 24.sp)
            }
            HorizontalDivider(
                modifier = Modifier.padding(top = 8.dp, start = screenWidth * 0.05f, end = screenWidth * 0.05f),
                thickness = 2.dp,
                color = Color.LightGray
            )
            Box(
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.1f)
                    .clickable
                    {
                        authViewModel.signout()

                    }
            ){
                Text(
                    text = "Log out",
                    fontFamily = customfont,
                    fontSize = 24.sp,
                    color = Color.Red)
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}








