package com.example.leaflove.ui.screen.plantscreen

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import com.example.leaflove.R
import com.example.leaflove.data.models.MyPlantModel
import com.example.leaflove.ui.components.SearchBar
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.compose.koinInject


@Composable
fun addmyplant(navHost: NavHostController)
{
    val authViewModel: AuthViewModel = koinInject()
    val plantViewModel: PlantViewModel = koinInject()
    val searchResults = plantViewModel.plantSearchList


    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold))

    var nameofplant by remember {
        mutableStateOf(TextFieldValue(""))
    }

    var typeofplant by remember {
        mutableStateOf("")
    }

    BoxWithConstraints (modifier = Modifier.fillMaxSize()){
        val height = maxHeight
        val width = maxWidth

        Box(
            modifier = Modifier
                .offset(y = height * 0.05f)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(height * 0.5f)
                    .width(width * 0.5f)
                    .align(Alignment.TopEnd)
                    .zIndex(0.2f)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.backgroundlogin1),
                    contentDescription = "background",
                    modifier = Modifier
                        .rotate(180f)
                        .align(Alignment.TopCenter)
                        .fillMaxSize()
                        .offset(y = height * 0.1f)
                        .alpha(0.7f)
                        .blur(6.dp),
                    contentScale = ContentScale.Fit
                )
            }

            Column(
                modifier = Modifier
                    .zIndex(1f)
                    .offset(y = height * 0.1f, x = width * 0.04f)
            )
            {
                Text(
                    text = "Add your plant",
                    fontSize = with(LocalDensity.current) { (width * 0.08f).toSp() },
                    fontFamily = customfont,
                    fontWeight = FontWeight.Bold,
                    color = BasicGreen
                )
                Spacer(modifier = Modifier.weight(0.1f))
                Text(
                    text = "Name of your plant",
                    fontSize = with(LocalDensity.current) { (width * 0.05f).toSp() },
                    fontFamily = customfont
                )
                Spacer(modifier = Modifier.weight(0.05f))

                OutlinedTextField(
                    value = nameofplant,
                    onValueChange = { nameofplant = it },
                    label = { Text(text = "Your plant's name") },
                    shape = RoundedCornerShape(50),
                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = -(width * 0.04f))
                        .padding(horizontal = width * 0.01f)
                        .background(Color.White),// Padding setelah clip dan background
                )

                Spacer(modifier = Modifier.weight(0.1f))


                Text(
                    text = "Type of your plant",
                    fontFamily = customfont,
                    fontSize = with(LocalDensity.current) { (width * 0.05f).toSp() },
                )
                Spacer(modifier = Modifier.weight(0.05f))

                Box(
                    modifier = Modifier
                        .offset(x = -(width * 0.04f))
                        .padding(horizontal = width * 0.01f)
                        .zIndex(0.5f)
                        .height(height = (height*0.3f))
                ){

                    SearchBar(
                        onQueryChange = { query ->
                            plantViewModel.fetchPlantSearchList(query)
                            if(query == ""){
                                plantViewModel.clearSearchResults()
                            }
                        },
                        searchResults = searchResults
                    )

                }

                Button(
                    onClick = {
                        Log.d("Firestore", authViewModel.userData.value.toString())
                        authViewModel.updateuserMyPlant(
                            newMyPlant = MyPlantModel(
                                plant_name = nameofplant.text,
                                plant_fk = plantViewModel.plantSelectedItem.value?.id ?: 1
                            )
                        )},

                    modifier = Modifier
                        .fillMaxWidth()
                        .offset(x = -(width * 0.04f), y = 50.dp)
                        .padding(horizontal = width * 0.01f, )
                        .zIndex(0.9f),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = BasicGreen,
                        disabledContentColor = BasicGreen,
                        disabledContainerColor = Color.White
                    )
                ) {
                    Text(
                        text = "Add to my Plant",
                        fontFamily = customfont,
                        fontWeight = FontWeight.Bold,
                        fontSize = with(LocalDensity.current) { (width * 0.05f).toSp() }
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }

}