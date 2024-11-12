package com.example.leaflove.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.models.DefaultImage
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.gson.Gson

data class encyclo(
    val nama: String,
    val namalatin: String,
    val image: Int
)

@Composable
fun PlantListItem(encyclo: PlantSpeciesEntity, plantViewModel: PlantViewModel, navHost: NavHostController) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(Color.White)
                .padding(16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically)
            {

                encyclo.default_image?.let {
                    Log.d("Check Image", it)
                    if(it == "null"){
                        Image(painter = painterResource(R.drawable.backgroundlogin1) , modifier = Modifier.size(64.dp), contentDescription = null)
                    } else {
                        val data = convertIntoImage(it)
                        Image(painter = rememberAsyncImagePainter(data), modifier = Modifier.size(64.dp), contentDescription = null)

                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    encyclo.common_name?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    encyclo.scientific_name?.let {
                        Text(
                            text = it,
                            fontSize = 14.sp,
                            color = Color.Gray
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(onClick = { fetchPlantDetailsFromRoom(encyclo.id, plantViewModel, navHost) }) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        contentDescription = "Next"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
    }
    }
}

fun convertIntoImage(jsonString: String): String? {
    val data = Gson().fromJson(jsonString, DefaultImage::class.java)
    val image = data.thumbnail;
    return image
}

fun fetchPlantDetailsFromRoom(id: Int, plant: PlantViewModel, navHost: NavHostController){
    plant.fetchPlantDetails(id)
    navHost.navigate("detailscreen")
    Log.d("Test plant detail", plant.plantDetail.toString())
}