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
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun myplantdetail(navHost: NavHostController)
{
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
                .offset(y = screenHeight * 0.1f)
        ) {
            Row {
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = "Ted",
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Top",
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    fontSize = 28.sp,
                    color = Color.LightGray
                )
                Spacer(modifier = Modifier.weight(0.1f))
            }
            Spacer(modifier = Modifier.weight(1f))
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
                        .height(screenHeight * 0.5f)
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
                                        text = "Status",
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
                                        text = "Status",
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
                    }
                }
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
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
                    Text(
                        text = "FunFact",
                        fontFamily = customfont,
                        fontSize = 20.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .align(Alignment.Center))
                }
            }


            Spacer(modifier = Modifier.weight(6f))

        }
    }
}