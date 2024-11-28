package com.example.leaflove.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.leaflove.R
import com.example.leaflove.data.entities.PlantSpeciesEntity
import com.example.leaflove.data.models.MyPlantModel
import com.example.leaflove.data.models.PlantSpecies
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import org.koin.compose.koinInject




@Composable
fun SearchBar(
    onQueryChange: (String) -> Unit,
    searchResults: State<List<PlantSpeciesEntity>>
) {
    val query = remember { mutableStateOf("") }
    val plantViewModel: PlantViewModel = koinInject()
    val authViewModel: AuthViewModel = koinInject()


    var customfont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )
    Column() {
        TextField(
            value = query.value,
            onValueChange = {

                query.value = it
                onQueryChange(it)
                if(query.value == ""){
                    plantViewModel.clearSearchResults()
                }
                // Trigger search updates
            },
            placeholder = {
                Text(
                    text = "Search your plant",
                    color = Color.LightGray
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.search_icon),
                    contentDescription = "Search"
                )
            },
            modifier = Modifier.fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.Black
            ),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 16.sp,
                color = Color.Black
            ),
            singleLine = true
        )

        // Show suggestions dynamically
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(searchResults.value) { plant ->
                plant.common_name?.let {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Button(
                            onClick = {
                                plantViewModel.selectItem(plant)
                                query.value = plant.common_name
                                plantViewModel.clearSearchResults()
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent,
                                contentColor = Color.Black
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                                .drawBehind {
                                    // Draw a bottom outline
                                    val strokeWidth = 2.dp.toPx()
                                    val y = size.height - strokeWidth / 2
                                    drawLine(
                                        color = Color.LightGray, // Outline color
                                        start = Offset(0f, y),
                                        end = Offset(size.width, y),
                                        strokeWidth = strokeWidth
                                    )
                                }
                        ) {
                            Text(
                                text = it,
                                modifier = Modifier.fillMaxWidth(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }

        }

    }
}
