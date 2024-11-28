package com.example.leaflove.ui.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.viewmodel.PlantViewModel

@Composable
fun ModelARCard(
    encyclo: PlantSpeciesEntity,
    onClick: () -> Unit,
    screenHeight: Dp, screenWidth: Dp
) {
    // Menghitung padding untuk menempatkan box ke tengah
    val sizeOuter = screenHeight * 0.12f
    val sizeInner = screenHeight * 0.1f
    val paddingCenter = (sizeOuter - sizeInner) / 2

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .height(sizeOuter)
            .width(sizeOuter)
            .background(Color.White)
            .padding(paddingCenter)
            .clickable { onClick() } // Make the card clickable// Padding luar untuk aesthetics
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .size(sizeInner)
                .align(Alignment.Center)
        ) {
            encyclo.default_image?.let { imageUrl ->
                Log.d("Check Image", imageUrl)
                if (imageUrl == "null") {
                    Image(
                        painter = painterResource(R.drawable.backgroundlogin1),
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                } else {
                    val imageData = convertIntoImage(imageUrl)
                    Image(
                        painter = rememberAsyncImagePainter(imageData),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        contentDescription = null
                    )
                }
            }
        }
    }
}