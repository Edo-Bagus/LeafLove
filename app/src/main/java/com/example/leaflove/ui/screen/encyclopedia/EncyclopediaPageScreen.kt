package com.example.leaflove.ui.screen.encyclopedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.ui.components.PlantListItem
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun EncyclopediaMainScreen(navHost: NavHostController) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .zIndex(0.8f)
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // Wrapping the entire content in a scrollable column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()) // Allow scrolling if content overflows
        ) {
            // Top section with green background and filter row
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.5f) // Top section height
                    .width(screenWidth)// Rounded bottom corners
                    .background(Color.White)
                    .clip(shape = RoundedCornerShape(0.dp, 0.dp, 50.dp, 0.dp))// Green background
            ) {
                Box(modifier = Modifier
                    .background(BasicGreen)
                    .fillMaxSize())
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        // Coin, Search bar, or any other top-level content can go here
                        Text(
                            text = "Top Search",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(35.dp))
            PlantListItem(imageresID = R.drawable.cart, nama = "percobaan", description = "apaaaaaaaa") // Example plant item
            Spacer(modifier = Modifier.height(8.dp))
            PlantListItem(imageresID = R.drawable.outline_forest_24, nama = "nama tumbuhan ni", description = "napppaaaaaa") // Example plant item
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1f)
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // Wrapping the entire content in a scrollable column
        Box(modifier = Modifier.offset(y = screenHeight * 0.45f))
        {
            CategoryFilterRow()
        }
    }
}

@Composable
fun CategoryFilterRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        CategoryChip(label = "All", count = 8, isSelected = true)
        CategoryChip(label = "Outdoor", count = 4, isSelected = false)
        CategoryChip(label = "Indoor", count = 4, isSelected = false)
    }
}

@Composable
fun CategoryChip(label: String, count: Int, isSelected: Boolean) {
    val backgroundColor = if (isSelected) Color.Black else Color.White
    val textColor = if (isSelected) Color.White else Color.Black

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(8.dp)
    ) {
        // Circular button for the count
        Box(
            modifier = Modifier
                .size(60.dp) // Circular size
                .clip(CircleShape)
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$count",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = textColor
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Label under the circle
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            color = Color.Black
        )
    }
}
