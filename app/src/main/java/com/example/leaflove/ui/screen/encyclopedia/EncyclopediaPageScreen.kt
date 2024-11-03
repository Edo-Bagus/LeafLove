package com.example.leaflove.ui.screen.encyclopedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.leaflove.ui.components.encyclo
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.PlantViewModel

@Composable
fun EncyclopediaMainScreen(navHost: NavHostController, viewModel: PlantViewModel) {
    LaunchedEffect(Unit) {
        viewModel.initializeDAOPlant()
        viewModel.fetchPlantListFromRoom()
        Log.d("Test", viewModel.plantList.toString())
    }
    val plants = viewModel.plantList.value

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
            // Top green section
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.4f) // Occupies the top 40% of the screen
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 50.dp))
                    .background(BasicGreen),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Top Search",
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    color = Color.White
                )
            }

            // Scrollable plant list section
            Column(
                modifier = Modifier
                    .weight(1f) // Take remaining space
                    .verticalScroll(rememberScrollState())
                    .offset(y = 28.dp)
            ) {
                plants.forEach { plant ->
                    PlantListItem(encyclo = plant)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
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
        Box(modifier = Modifier.offset(y = screenHeight * 0.33f))
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
            .padding(16.dp),
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
