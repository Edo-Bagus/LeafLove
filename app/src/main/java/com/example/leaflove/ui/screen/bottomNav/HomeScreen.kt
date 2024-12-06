package com.example.leaflove.ui.screen.bottomNav

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.material3.Text
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.services.LocationUtils
import com.example.leaflove.viewmodel.LocationViewModel
import com.example.leaflove.R
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.WeatherViewModel
import com.example.leaflove.ui.theme.ButtonGreen
import com.example.leaflove.utils.calculatePlantAgeInDays
import com.example.leaflove.utils.formatDate
import com.example.leaflove.viewmodel.AuthViewModel
import org.koin.compose.koinInject
import com.example.leaflove.viewmodel.PlantViewModel

val customfont = FontFamily(
    Font(R.font.baloo_font, weight = FontWeight.Normal),
    Font(R.font.baloo_bold, weight = FontWeight.Bold)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navHost: NavHostController) {

    val authViewModel = koinInject<AuthViewModel>()
    val plantViewModel = koinInject<PlantViewModel>()
    val weatherViewModel = koinInject<WeatherViewModel>()
    val locationViewModel = koinInject<LocationViewModel>()

    val plants = mutableListOf<Plant>()
    for(plant in authViewModel.userData.value?.my_plants!!){
        val status = Status.values().find { it.rating == plant.plant_status } ?: Status.Mediocre
        plants.add(Plant(
            nama = plant.plant_name,
            status = status.name,
            image = plant.plant_image_url,
            to_water = formatDate(plant.plant_to_be_watered),
            last_water = formatDate(plant.plant_last_watered),
            age = calculatePlantAgeInDays(plant.plant_age)
        ))
    }
    val plant = remember(plants) { plants.randomOrNull() }


    val context = LocalContext.current
    plantViewModel.loadFunFacts(context)
    val funFacts = remember(plantViewModel.funFacts.value) {
        plantViewModel.funFacts.value.random()
    }
    val locationUtils = remember { LocationUtils(context, locationViewModel) }
    val location = locationViewModel.locationState

    val cuaca = weatherViewModel.weatherState.value.weather?.firstOrNull()?.main ?: "Loading"
    val humidity = weatherViewModel.weatherState.value.main?.humidity ?: "Loading"
    val tempKelvin = weatherViewModel.weatherState.value.main?.temp ?: 99999.99
    var tempCelcius = tempKelvin?.minus(273)
    var tempCelciusString = String.format("%.2f", tempCelcius)

    // Start location updates when the composable is entered
    LaunchedEffect(Unit) {
        if (locationUtils.hasLocationPermission(context)) {
            locationUtils.requestLocationUpdate()
            weatherViewModel.fetchWeather(location.value.latitude, location.value.longitude)
        } else {
            requestLocationPermissions(context)
        }
    }



    DisposableEffect(Unit) {
        onDispose {
            locationUtils.stopLocationUpdates()
        }
    }

    // Use BoxWithConstraints to get the screen size
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        val scaffoldState = rememberBottomSheetScaffoldState()

        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetContainerColor = Color.White,
            sheetPeekHeight = screenHeight * 0.5f,
            sheetContent = {
                Column(
                    modifier = Modifier
                        .heightIn(max = screenHeight * 0.86f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.weight(0.1f))
                    WeatherCard(
                        screenHeight = screenHeight,
                        screenWidth = screenWidth,
                        weather = cuaca,
                        humidity = humidity.toString(),
                        temperature = tempCelciusString
                    )
                    Spacer(modifier = Modifier.weight(0.5f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        FeatureCard(
                            navHost = navHost,
                            screenWidth = screenWidth,
                            route = "transaction",
                            image = R.drawable.cart,
                            text = "Store"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        FeatureCard(
                            navHost = navHost,
                            screenWidth = screenWidth,
                            route = "camera",
                            image = R.drawable.baseline_camera_alt_24_selected,
                            text = "AR"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        FeatureCard(
                            navHost = navHost,
                            screenWidth = screenWidth,
                            route = "my_plant",
                            image = R.drawable.baseline_forest_24_selected,
                            text = "My Plant"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    }

                    Spacer(modifier = Modifier.weight(0.5f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        FeatureCard(
                            navHost = navHost,
                            screenWidth = screenWidth,
                            route = "searchscreen",
                            image = R.drawable.baseline_search_24,
                            text = "Encyclopedia"
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        FunFactCard(
                            screenWidth = screenWidth,
                            screenHeight = screenHeight,
                            funFacts = funFacts
                        )
                        Spacer(modifier = Modifier.weight(1f))

                    }

                    Spacer(modifier = Modifier.weight(0.5f))
                    RandomMyPlantCard(
                        plant,
                        screenWidth,
                        screenHeight
                    )
                    Spacer(modifier = Modifier.weight(1.5f))


                }
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(screenHeight * 0.6f)
                    .zIndex(0.8f)
                    .background(Color.Black)
                    .fillMaxSize()
            ) {
                Button(
                    onClick = { navHost.navigate("addmyplant") },
                    modifier = Modifier
                        .zIndex(1f)
                        .offset(x = screenWidth * 0.02f, y = screenHeight * 0.2f)
                        .height(screenHeight * 0.08f)
                        .shadow(
                            elevation = 8.dp, // Tinggi bayangan
                            shape = RoundedCornerShape(10.dp), // Sama dengan radius sudut tombol
                            clip = false // Tidak memotong konten berdasarkan bentuk
                        ),
                    colors = ButtonColors(
                        contentColor = BasicGreen,
                        containerColor = Color.White,
                        disabledContentColor = BasicGreen,
                        disabledContainerColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = " + Add your plant",
                        fontFamily = customfont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp)
                }
                Image(
                    painter = painterResource(R.drawable.mainmenubg),
                    contentDescription = "Background",
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(0.1f),
                    contentScale = ContentScale.Crop)
                Image(
                    painter = painterResource(R.drawable.contoh_tanaman),
                    contentDescription = "Contoh Tanaman",
                    modifier = Modifier
                        .zIndex(1f)
                        .size(size = screenHeight * 0.5f)
                        .offset(x = screenWidth * 0.3f, y = screenHeight * 0.03f)
                )
            }
        }
    }
}

@Composable
fun WeatherCard(
    screenHeight: Dp,
    screenWidth: Dp,
    weather: String,
    humidity: String,
    temperature: String
){
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .background(ButtonGreen)
            .width(screenWidth * 0.8f)
            .height(screenHeight * 0.1f)
    ) {
        Row (modifier = Modifier
            .align(Alignment.Center) ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = weather,
                color = Color.White,
                fontFamily = customfont
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = humidity,
                color = Color.White,
                fontFamily = customfont
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "$temperature˚C",
                fontFamily = customfont,
                color = Color.White)
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}

@Composable
fun FeatureCard(
    navHost: NavHostController,
    screenWidth: Dp,
    route: String,
    image: Int,
    text: String
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .background(Color.White)
            .size(screenWidth * 0.2f)
    ) {
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .clickable {
                    navHost.navigate(route)
                },
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = null,
                alignment = Alignment.Center,
                modifier = Modifier.size(screenWidth * 0.1f)
            )

            // Dynamic font size adjustment
            BoxWithConstraints {
                val maxWidth = maxWidth
                val fontSize = remember(text) { mutableStateOf(16.sp) } // Default font size

                // Measure the text and adjust font size
                Text(
                    text = text,
                    fontSize = fontSize.value,
                    fontFamily = customfont,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.hasVisualOverflow) {
                            fontSize.value *= 0.8f // Reduce font size by 10%
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FunFactCard(
    screenWidth: Dp,
    screenHeight: Dp,
    funFacts: String
){
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .background(Color.White)
            .width(screenWidth * 0.5f)
            .height(screenHeight * 0.1f)
    ){
        Text(text = funFacts,
            modifier = Modifier
                .align(Alignment.Center),
            textAlign = TextAlign.Center,
            fontSize = 14.sp,
            fontFamily = customfont)
    }
}

@Composable
fun RandomMyPlantCard(
    plant: Plant?,
    screenWidth: Dp,
    screenHeight: Dp,
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 10.dp,
                shape = RoundedCornerShape(20.dp),
                clip = false
            )
            .background(Color.White)
            .width(screenWidth * 0.8f)
            .height(screenHeight * 0.2f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(modifier = Modifier.weight(0.1f))

            Box(
                modifier = Modifier
                    .shadow(
                        elevation = 10.dp,
                        shape = RoundedCornerShape(10.dp),
                        clip = false
                    )
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color.White)
                    .size(screenWidth * 0.2f)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(plant?.image),
                    contentDescription = plant?.nama,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))

            Column(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(8.dp) // Ruang di sekitar teks
            ) {
                AdjustableText(
                    text = plant?.nama ?: "Unknown",
                    fontFamily = customfont,
                    initialFontSize = 18.sp,
                    minFontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.width(screenWidth * 0.5f)
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.01f))
                AdjustableText(
                    text = "Status: ${plant?.status ?: "Unknown"}",
                    fontFamily = customfont,
                    initialFontSize = 14.sp,
                    minFontSize = 10.sp,
                    modifier = Modifier.width(screenWidth * 0.5f)
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.01f))
                AdjustableText(
                    text = "To Water: ${plant?.last_water ?: "Unknown"}",
                    fontFamily = customfont,
                    initialFontSize = 14.sp,
                    minFontSize = 10.sp,
                    modifier = Modifier.width(screenWidth * 0.5f)
                )
                Spacer(modifier = Modifier.height(screenHeight * 0.01f))
                AdjustableText(
                    text = "To Water: ${plant?.to_water ?: "Unknown"}",
                    fontFamily = customfont,
                    initialFontSize = 14.sp,
                    minFontSize = 10.sp,
                    modifier = Modifier.width(screenWidth * 0.5f)
                )
            }

            Spacer(modifier = Modifier.weight(0.1f))
        }
    }
}

@Composable
fun AdjustableText(
    text: String,
    modifier: Modifier = Modifier,
    initialFontSize: TextUnit = 18.sp,
    minFontSize: TextUnit = 10.sp,
    fontWeight: FontWeight = FontWeight.Bold,
    fontFamily: FontFamily = FontFamily.Default
) {
    // State untuk ukuran teks
    var fontSize by remember { mutableStateOf(initialFontSize) }

    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        maxLines = 1,
        overflow = TextOverflow.Clip,
        modifier = modifier,
        softWrap = false,
        onTextLayout = { result ->
            if (result.didOverflowWidth) {
                val newFontSize = (fontSize.value - 1).coerceAtLeast(minFontSize.value)
                fontSize = newFontSize.sp
            }
        }
    )
}

private fun requestLocationPermissions(context: Context) {
    if (context is Activity) {
        // Check if the permission is not granted
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }
}

const val REQUEST_LOCATION_PERMISSION = 1
