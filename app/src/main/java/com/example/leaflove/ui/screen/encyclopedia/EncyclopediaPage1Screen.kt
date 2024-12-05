package com.example.leaflove.ui.screen.encyclopedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.ui.components.convertIntoImage
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.utils.cleanString
import com.example.leaflove.utils.parseStringList
import com.example.leaflove.viewmodel.AuthState
import com.example.leaflove.viewmodel.PlantViewModel
import org.koin.compose.koinInject

@Composable
fun EncyclopediaDetailScreen(navHost: NavHostController)
{

    val plantViewModel = koinInject<PlantViewModel>()

    val customFont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )

    val plantDetailEntity = plantViewModel.plantDetail.value
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()

    ){
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        if (plantDetailEntity == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // Optional dimmed background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White, // Customize as needed
                    strokeWidth = 4.dp
                )
            }
        } else {
            Row(
                modifier = Modifier.fillMaxSize()

            ) {
                // Gambar kiri
                Box (modifier = Modifier
                    .width(screenWidth * 0.35f)
                    .align(Alignment.CenterVertically)){
                    plantDetailEntity.default_image?.let {
                        Log.d("Check Image", it)
                        if(it == "null"){
                            Image(painter = painterResource(R.drawable.backgroundlogin1) , modifier = Modifier.size(128.dp), contentDescription = null)
                        } else {
                            val data = convertIntoImage(it)
                            Image(painter = rememberAsyncImagePainter(data), modifier = Modifier.size(128.dp), contentDescription = null)

                        }
                    }
                }

                // Detail kanan
                Box(modifier = Modifier
                    .clip(RoundedCornerShape(topStart = 20.dp))
                    .background(BasicGreen)
                    .fillMaxSize()
                )
                {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()) //scroll
                            .padding(
                                start = screenWidth * 0.1f, // Posisi horizontal
                                top = screenHeight * 0.1f, // Posisi vertikal
                            )
                    ){

                        Text(text = "${plantDetailEntity.common_name}",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFont,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 0.dp))
                        Spacer(modifier = Modifier.height(16.dp))


                        Text(text =  "${plantDetailEntity.description}",
                            color = Color.White,
                            fontSize = 16.sp,
                            fontFamily = customFont,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(0.dp, 0.dp, 20.dp, 0.dp))
                        Spacer(modifier = Modifier.height(16.dp))

                        plantDetailEntity.scientific_name?.let { PointDetail(title = "Scientific Name", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.other_name?.let { PointDetail(title = "Other Name", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.propagation?.let { PointDetail(title = "Propagation", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.origin?.let { PointDetail(title = "Origin", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.sunlight?.let { PointDetail(title = "Sunlight", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.pruning_month?.let { PointDetail(title = "Pruning Month", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.soil?.let { PointDetail(title = "Soil", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.pest_susceptibility?.let { PointDetail(title = "Pest Susceptibility", attribute = it, customFontFamily = customFont) }
                        plantDetailEntity.leaf_color?.let { PointDetail(title = "Leaf Color", attribute = it, customFontFamily = customFont) }

                        Text(text = "Other Attribute",
                            color = Color.White,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = customFont,
                            textAlign = TextAlign.Justify)
                        Spacer(modifier = Modifier.height(8.dp))

                        plantDetailEntity.let { DisplayPlantDetails(it,customFontFamily = customFont) }



                        Button(
                            onClick = {
                                navHost.navigate("camera") },
                            modifier = Modifier
                                .width(screenWidth * 0.5f)
                                .height(screenHeight * 0.05f),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                        ) {
                            Text(text = "Try with AR",
                                color = BasicGreen)
                        }
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun PointDetail(title: String, attribute: String,customFontFamily: FontFamily){
    val itemList = attribute.let { parseStringList(it) }

    if(itemList.isNotEmpty()){
        Text(text = title,
            color = Color.White,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = customFontFamily,
            textAlign = TextAlign.Justify)
        Spacer(modifier = Modifier.height(8.dp))

        // Dynamically add text items
        itemList.forEach { item ->
            Text(
                text = cleanString(item),
                color = Color.White,
                fontFamily = customFontFamily,
                fontSize = 16.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}



@Composable
fun DisplayPlantDetails(plant: PlantDetailEntity, customFontFamily: FontFamily) {
        // Example of displaying boolean attributes
        plant.thorny?.takeIf { it }?.let {
            Text(text = "Thorny",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.drought_tolerant?.takeIf { it }?.let {
            Text(text = "Drought Tolerant",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.salt_tolerant?.takeIf { it }?.let {
            Text(text = "Salt Tolerant",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.invasive?.takeIf { it }?.let {
            Text(text = "Invasive",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.tropical?.takeIf { it }?.let {
            Text(text = "Tropical",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.indoor?.takeIf { it }?.let {
            Text(text = "Indoor",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.flowers?.takeIf { it }?.let {
            Text(text = "Has Flowers",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.cones?.takeIf { it }?.let {
        Text(text = "Has Cones",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = customFontFamily)
        }

        plant.fruits?.takeIf { it }?.let {
        Text(text = "Has Fruits",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = customFontFamily)
        }

        plant.edible_fruit?.takeIf { it }?.let {
        Text(text = "Edible Fruits",
            color = Color.White,
            fontSize = 16.sp,
            fontFamily = customFontFamily)
        }
        plant.leaf?.takeIf { it }?.let {
            Text(text = "Has leaf",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.edible_leaf?.takeIf { it }?.let {
            Text(text = "Edible Leaf",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.cuisine?.takeIf { it }?.let {
            Text(text = "Cuisine",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }

        plant.medicinal?.takeIf { it }?.let {
            Text(text = "Medicinal",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = customFontFamily)
        }
    Spacer(modifier = Modifier.height(16.dp))
}

