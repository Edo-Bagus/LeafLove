package com.example.leaflove.ui.screen.encyclopedia

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.ui.components.convertIntoImage
import com.example.leaflove.ui.screen.storescreen.StoreScreen
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.PlantViewModel
import org.w3c.dom.Text

@Composable
fun EncyclopediaDetailScreen(navHost: NavHostController, plantViewModel: PlantViewModel)
{
    val plantDetailEntity = plantViewModel.plantDetail
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()

    ){
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Row(
            modifier = Modifier.fillMaxSize()

        ) {
            // Gambar kiri
            Box (modifier = Modifier
                .width(screenWidth * 0.35f)
                .align(Alignment.CenterVertically)){
                plantDetailEntity.value.default_image?.let {
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
                        .fillMaxSize() // Memastikan Column mengambil ruang penuh
                        .verticalScroll(rememberScrollState()) // Mengaktifkan scroll
                        .padding(
                            start = screenWidth * 0.1f, // Posisi horizontal
                            top = screenHeight * 0.1f, // Posisi vertikal
                            // Tambahkan padding bawah untuk ruang ekstra
                        )
                     ){
                    // Plant Name
                    Text(text = "${plantDetailEntity.value.common_name}",
                        color = Color.White,
                        fontSize = 24.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    Text(text =  "${plantDetailEntity.value.description}",
                        color = Color.White,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp,0.dp,20.dp,0.dp))

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(text = "Tips",
                        color = Color.White,
                        fontSize = 24.sp,
                        )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = "Volume Water Requirement ${plantDetailEntity.value.volume_water_requirement}",
                        color = Color.White,
                        fontSize = 16.sp)
                    Text(text = "Place indoor/outdoor ${plantDetailEntity.value.indoor}",
                        color = Color.White,
                        fontSize = 16.sp)
                    Text(text = "Sunlight ${plantDetailEntity.value.sunlight}",
                        color = Color.White,
                        fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = { navHost.navigate("camera") },
                        modifier = Modifier
                            .width(screenWidth * 0.5f)
                            .height(screenHeight * 0.05f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Try with AR", color = BasicGreen  )
                    }
                    Spacer(modifier = Modifier.height(100.dp))
                }
            }
        }
    }
}
