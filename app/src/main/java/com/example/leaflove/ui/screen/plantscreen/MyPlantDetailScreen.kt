package com.example.leaflove.ui.screen.plantscreen

import android.annotation.SuppressLint
import android.util.Log
import android.widget.Space
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.data.models.MyPlantModel
import com.example.leaflove.ui.screen.bottomNav.Status
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.utils.calculatePlantAgeInDays
import com.example.leaflove.utils.formatDate
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.MyPlantViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.firebase.Timestamp
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun myplantdetail(navHost: NavHostController)
{
    var authViewModel = koinInject<AuthViewModel>()
    var myPlantViewModel = koinInject<MyPlantViewModel>()

    // Clear fun fact list when exiting the page (when the composition is disposed)
    DisposableEffect(Unit) {
        onDispose {
            myPlantViewModel.funFactList.value = emptyList()
        }
    }

    var plant = remember {
        Log.d("Tes: ", authViewModel.selectedPlant.value.toString())
        authViewModel.selectedPlant.value
    }

// Use LaunchedEffect to ensure fun fact is fetched only once per page load
    LaunchedEffect(plant) {
        if (plant != null) {
            myPlantViewModel.loadFunFactList(plant.plant_fk, plant.plant_name)
        }
    }

    var funFact by remember { mutableStateOf<String?>(null) }

// Only update funFact if the list is populated and not already set
    LaunchedEffect(myPlantViewModel.funFactList.value) {
        if (myPlantViewModel.funFactList.value.isNotEmpty() && funFact == null) {
            funFact = myPlantViewModel.funFactList.value.random()
        }
    }

    var name = remember {
        plant?.plant_name ?: "Plant"
    }

    var age = remember {
        val plantAge = plant?.plant_age
        if (plantAge != null) {
            calculatePlantAgeInDays(plantAge)  // Pass the Timestamp if it's not null
        } else {
            "Age"  // Default value if plant_age is null
        }
    }

    var status = remember {
        Status.values().find { it.rating == plant?.plant_status } ?: Status.Mediocre
    }

    var last_water = remember {
        val plantLastWater = plant?.plant_last_watered
        if (plantLastWater != null) {
            formatDate(plantLastWater)  // Pass the Timestamp if it's not null
        } else {
            "Last Water"  // Default value if plant_age is null
        }
    }

    var to_water = remember {
        val plantToWater = plant?.plant_to_be_watered
        if (plantToWater != null) {
            formatDate(plantToWater)  // Pass the Timestamp if it's not null
        } else {
            "To Water"  // Default value if plant_age is null
        }
    }
    val customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Column(
            modifier = Modifier
                .offset(y = screenHeight * 0.08f)
        ) {
            Row {
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = name,
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Age: " + age,
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }
            Spacer(modifier = Modifier.weight(0.5f))
            Row {
                Spacer(modifier = Modifier.weight(1f))
                Box(
                    modifier = Modifier
                        .offset(x = screenWidth * 0.02f)
                        .height(screenHeight * 0.5f)
                ) {
                    Box(
                        modifier = Modifier
                            .height(screenHeight * 0.5f)
                            .width(screenWidth * 0.5f)
                            .clip(RoundedCornerShape(20))
                    ) {
                        Image(
                            painter = painterResource(R.drawable.contoh_tanaman),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }

                }
                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .height(screenHeight * 0.55f)
                        .width(screenWidth * 0.4f)
                )
                {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                    ) {
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .size(screenWidth * 0.28f)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .background(BasicGreen)
                                    .fillMaxSize()
                            )
                            {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = status.toString(),
                                        fontFamily = customfont,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Status",
                                        fontFamily = customfont,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(screenWidth * 0.28f)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .background(BasicGreen)
                                    .fillMaxSize()
                            )
                            {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = last_water,
                                        fontFamily = customfont,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Last Watering",
                                        fontFamily = customfont,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Box(
                            modifier = Modifier
                                .size(screenWidth * 0.28f)
                                .clip(RoundedCornerShape(20.dp))
                        )
                        {
                            Box(
                                modifier = Modifier
                                    .background(BasicGreen)
                                    .fillMaxSize()
                            )
                            {
                                Column(
                                    modifier = Modifier
                                        .align(Alignment.Center),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Text(
                                        text = to_water,
                                        fontFamily = customfont,
                                        fontSize = 22.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                    )
                                    Text(
                                        text = "Next Watering",
                                        fontFamily = customfont,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = Color.LightGray,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))

                        Box(
                            modifier = Modifier
                                .width(screenWidth * 0.28f)
                                .height(screenWidth * 0.2f)
                                .clip(RoundedCornerShape(20.dp))
                        ){
                            Button(
                                onClick = { authViewModel.selectedPlant.value?.let {
                                    authViewModel.updateUserMyPlantLast_Watered(
                                        it
                                    ); last_water = formatDate(timestamp = Timestamp.now()); to_water = formatDate(timestamp = Timestamp.now())
                                }},
                                modifier = Modifier
                                    .zIndex(1f)
                                    .fillMaxSize()
                                    .shadow(
                                        elevation = 8.dp, // Tinggi bayangan
                                        shape = RoundedCornerShape(10.dp), // Sama dengan radius sudut tombol
                                        clip = false // Tidak memotong konten berdasarkan bentuk
                                    ),
                                colors = ButtonColors(
                                    contentColor = Color.White,
                                    containerColor = BasicGreen,
                                    disabledContentColor = BasicGreen,
                                    disabledContainerColor = Color.White
                                ),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(
                                    text = "Watering",
                                    fontFamily = customfont,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp)
                            }
                        }


                        Spacer(modifier = Modifier.weight(1f))

                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(0.7f))
            Box(
                modifier = Modifier
                    .padding(10.dp)
                    .shadow(
                        elevation = 5.dp,
                        shape = RoundedCornerShape(5.dp),
                        clip = false
                    )
                    .height(screenHeight * 0.15f)
                    .clip(RoundedCornerShape(10.dp))
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .clip(RoundedCornerShape(10.dp))
                        .align(Alignment.Center)
                ) {
                    funFact?.let {
                        Text(
                            text = it,
                            fontFamily = customfont,
                            fontSize = 20.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.Center))
                    }
                }
            }


            Spacer(modifier = Modifier.weight(5f))

        }
    }
}