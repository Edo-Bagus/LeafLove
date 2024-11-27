package com.example.leaflove.ui.screen.encyclopedia

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantDetailEntity
import com.example.leaflove.ui.theme.BasicGreen

@Composable
fun EncyclopediaDetailScreen(navHost: NavHostController, plantDetailEntity: State<PlantDetailEntity>)
{
    BoxWithConstraints (
        modifier = Modifier
            .fillMaxSize()

    ){
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Box (modifier = Modifier
                .width(screenWidth * 0.35f)
                .align(Alignment.CenterVertically)){
                Image(
                    painter = painterResource(R.drawable.contoh_tanaman),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize())
            }

            Box(modifier = Modifier
                .clip(RoundedCornerShape(topStart = 50.dp))
                .background(BasicGreen)
                .width(screenWidth * 0.65f)
                .fillMaxHeight()
            )
            {
                Column (modifier = Modifier
                    .offset(x = screenWidth * 0.1f, y = screenHeight * 0.1f)){
                    Text(text = "Nama ${plantDetailEntity.value.common_name}", color = Color.White, fontSize = 24.sp)
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(text = "Deskripsi ${plantDetailEntity.value.description}", color = Color.White)
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(text = "Tips", color = Color.White, fontSize = 24.sp)
                    Spacer(modifier = Modifier.weight(0.1f))
                    Text(text = "Volume Water Requirement ${plantDetailEntity.value.volume_water_requirement}", color = Color.White)
                    Text(text = "Place indoor/outdoor ${plantDetailEntity.value.indoor}", color = Color.White)
                    Text(text = "Sunlight ${plantDetailEntity.value.sunlight}", color = Color.White)
                    Spacer(modifier = Modifier.weight(0.5f))
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier
                            .width(screenWidth*0.5f)
                            .height(screenHeight * 0.05f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White)
                    ) {
                        Text(text = "Try with AR", color = BasicGreen  )
                    }
                    Spacer(modifier = Modifier.weight(0.5f))
                }
            }
        }
    }
}