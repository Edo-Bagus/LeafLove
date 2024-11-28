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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun AboutScreen(navHost: NavHostController){
    val image = painterResource(R.drawable.edo)
    val image1 = painterResource(R.drawable.ilham)
    val image2 = painterResource(R.drawable.marshell)
    val image3 = painterResource(R.drawable.dafa)
    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )
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
                    .height(screenHeight * 0.3f)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 50.dp))
                    .background(BasicGreen),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .align(Alignment.CenterStart),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier
                        .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "About Us",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            fontFamily = customfont,
                            color = Color.White,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(0.dp,40.dp,0.dp,0.dp)
                        )
                        Text(
                            text = "Welcome to LeafLove\n" +
                                    "This Application will help you to manage and prepare anything for your plant. We also have a feature of AR so you can make a plan for your plant\n"+
                                    "This Application was created by Team 5 in PAPB Course.",
                            fontSize = 14.sp,
                            fontFamily = customfont,
                            color = Color.White,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp)
                            )
                    }

                    }
                }
            }
        Row {
            Column {
                Box(
                    modifier = Modifier
                        .padding(30.dp, 250.dp, 0.dp, .0.dp)
                        .size(125.dp)
                        .clip(RoundedCornerShape(8.dp))
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.edo),
                        contentDescription = "Image Edo",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(75.dp))
                    )
                }
                Text(text = "Eduardus Bagus W.",
                    fontSize = 16.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(30.dp,0.dp,0.dp,0.dp)
                                )
                Text(text = "22/493128/TK/53996",
                    fontSize = 14.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(30.dp,0.dp,0.dp,0.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(30.dp, 100.dp, 0.dp, 0.dp)
                        .size(125.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.marshell),
                        contentDescription = "Image Marcel",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(75.dp))
                    )
                }
                Text(text = "Marchel Rianra G. S.",
                    fontSize = 16.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(30.dp,0.dp,0.dp,0.dp)
                )
                Text(text = "22/494013/TK/54157",
                    fontSize = 14.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(30.dp,0.dp,0.dp,0.dp)
                )
            }
            Column {
                Box(
                    modifier = Modifier
                        .padding(70.dp, 250.dp, 0.dp, 0.dp)
                        .size(125.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.dafa),
                        contentDescription = "Image Dafa",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(75.dp))
                    )
                }
                Text(text = "Muhamad Daffa A. R.",
                    fontSize = 16.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(70.dp,0.dp,0.dp,0.dp)
                )
                Text(text = "22/503970/TK/55101",
                    fontSize = 14.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(70.dp,0.dp,0.dp,0.dp)
                )

                Box(
                    modifier = Modifier
                        .padding(70.dp, 100.dp, 0.dp, 0.dp)
                        .size(125.dp)
                )
                {
                    Image(
                        painter = painterResource(id = R.drawable.ilham),
                        contentDescription = "Image Ilham",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(125.dp)
                            .clip(RoundedCornerShape(75.dp))
                    )
                }
                Text(text = "Moh. Nazril Ilham",
                    fontSize = 16.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(70.dp,0.dp,0.dp,0.dp)
                )
                Text(text = "22/493142/TK/54000",
                    fontSize = 14.sp,
                    fontFamily = customfont,
                    modifier = Modifier.padding(70.dp,0.dp,0.dp,0.dp)
                )

            }
        }
    }
}
