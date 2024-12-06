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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun AboutScreen(navHost: NavHostController) {
    val customFont = FontFamily(
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
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.3f)
                    .clip(RoundedCornerShape(bottomEnd = screenWidth * 0.05f))
                    .background(BasicGreen),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.offset( y = screenWidth * 0.01f)
                ) {
                    Text(
                        text = "About Us",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        fontFamily = customFont,
                        color = Color.White
                    )
                    Text(
                        text = "Welcome to LeafLove\n" +
                                "This Application will help you to manage and prepare anything for your plant. We also have a feature of AR so you can make a plan for your plant.\n" +
                                "This Application was created by Team 5 in PAPB Course.",
                        fontSize = 14.sp,
                        fontFamily = customFont,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Member Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MemberCard(
                    imageRes = R.drawable.edo,
                    name = "Eduardus Bagus W.",
                    id = "22/493128/TK/53996",
                    customFont = customFont
                )
                MemberCard(
                    imageRes = R.drawable.dafa,
                    name = "Muhamad Daffa A. R.",
                    id = "22/503970/TK/55101",
                    customFont = customFont
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                MemberCard(
                    imageRes = R.drawable.marshell,
                    name = "Marchel Rianra G. S.",
                    id = "22/494013/TK/54157",
                    customFont = customFont
                )
                MemberCard(
                    imageRes = R.drawable.ilham,
                    name = "Moh. Nazril Ilham",
                    id = "22/493142/TK/54000",
                    customFont = customFont
                )
            }
        }
    }
}

@Composable
fun MemberCard(
    imageRes: Int,
    name: String,
    id: String,
    customFont: FontFamily
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.width(150.dp)
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = name,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(150.dp)
                .clip(RoundedCornerShape(80.dp))
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = name,
            fontSize = 14.sp,
            fontFamily = customFont,
            textAlign = TextAlign.Center
        )
        Text(
            text = id,
            fontSize = 12.sp,
            fontFamily = customFont,
            textAlign = TextAlign.Center,
            color = Color.Gray
        )
    }
}

