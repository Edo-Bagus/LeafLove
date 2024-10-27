package com.example.leaflove.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun header(navHost: NavController, modifier: Modifier = Modifier, screenWidth: Dp, screenHeight: Dp) {
    var search by remember { mutableStateOf("") }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // Optional padding for better spacing
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        Button(
            onClick = { /* TODO */ },
            colors = ButtonDefaults.buttonColors(containerColor = Color.White
            ),
            modifier = Modifier
                .height(screenHeight*0.056f)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(20.dp),
                    clip = false
                )

        ) {
            Text(text = "Coin", color = Color.LightGray)
        }

        Spacer(modifier = Modifier.weight(1f))
        val padding = screenWidth * 0.1f
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(screenHeight * 0.056f)
                .clip(RoundedCornerShape(50))
                .background(Color.Black)
                .clickable {
                    navHost.navigate("searchScreen") // Navigate on click
                }
        ) {
            TextField(
                value = search,
                onValueChange = { newText -> search = newText },
                placeholder = {
                    Text(
                        text = "Search",
                        color = Color.LightGray
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier.fillMaxSize(), // Ensures TextField fills the Box
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black
                ),
                textStyle = LocalTextStyle.current.copy(fontSize = 16.sp, color = Color.Black),
                singleLine = true
            )
        }


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                    navHost.navigate("transaction") {
                        popUpTo(navHost.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

            },
            modifier = Modifier
                .wrapContentSize()
                .height(screenHeight * 0.056f),
            colors = ButtonDefaults.buttonColors(containerColor = BasicGreen)
        ) {
            Icon(
                painter = painterResource(R.drawable.baseline_person_24_default),
                contentDescription = "Account"
            )
        }

        Spacer(modifier = Modifier.weight(0.1f))
    }
}
