package com.example.leaflove.ui.screen.encyclopedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
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
    val plants = viewModel.plantList.value
    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )

    DisposableEffect(Unit) {
        onDispose {
            viewModel.fetchPlantListFromRoom()
        }
    }

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
                    .height(screenHeight * 0.2f) // Occupies the top 40% of the screen
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(bottomEnd = 50.dp))
                    .background(BasicGreen)
                    .padding(30.dp, 60.dp)
            ) {
                Text(
                    text = "Top Search",
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 36.sp,
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
                    PlantListItem(encyclo = plant, viewModel, navHost)
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
        Box(
            modifier = Modifier
                .offset(x= screenWidth * 0.05f,y = screenHeight * 0.17f)
                .clip(RoundedCornerShape(10.dp))
        )
        {
            Surface(
                modifier = Modifier
                    .height(screenHeight * 0.05f)
                    .width(screenWidth * 0.6f),
                color = BasicGreen
            ) {
                DropDown(viewModel)
            }
        }
    }
}

@Composable
fun DropDown(viewModel: PlantViewModel) {

    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold))

    val isDropDownExpanded = remember {
        mutableStateOf(false)
    }

    val itemPosition = remember {
        mutableStateOf(-1)
    }

    val placeholder = "Sort"

    val usernames = listOf("Sort Asc by Common Name", "Sort Desc by Common Name", "Sort Asc by Scientific Name", "Sort Desc by Scientific Name")

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Box {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable {
                    isDropDownExpanded.value = true
                }
            ) {
                Text(
                    text = if (itemPosition.value == -1) placeholder else usernames[itemPosition.value],
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    color = if (itemPosition.value == -1) Color.Gray else Color.White)
                Image(
                    painter = painterResource(id = R.drawable.drop_down_ic),
                    contentDescription = "DropDown Icon"
                )
            }
            DropdownMenu(
                expanded = isDropDownExpanded.value,
                onDismissRequest = {
                    isDropDownExpanded.value = false
                }) {
                usernames.forEachIndexed { index, username ->
                    DropdownMenuItem(
                        text = { Text(text = username) },
                        onClick = {
                            isDropDownExpanded.value = false
                            itemPosition.value = index

                            when (index) {
                                0 -> viewModel.sortPlantList(by = "common_name", ascending = true)
                                1 -> viewModel.sortPlantList(by = "common_name", ascending = false)
                                2 -> viewModel.sortPlantList(by = "scientific_name", ascending = true)
                                3 -> viewModel.sortPlantList(by = "scientific_name", ascending = false)
                            }
                        }
                    )
                }
            }
        }

    }
}
