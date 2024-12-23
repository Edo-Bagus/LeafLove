package com.example.leaflove.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.leaflove.R

data class Store(
    val nama: String,
    val price: String,
    val image: Int
)

// Fungsi untuk menampilkan satu kartu Plant
@Composable
fun StoreCard(store: Store, screenHeight: Dp, screenWidth: Dp) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .offset(x = screenWidth * 0.01f)
            .clip(RoundedCornerShape(20.dp))
    ) {
        Box(
            modifier = Modifier
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(10.dp),
                    ambientColor = Color.Gray.copy(alpha = 0.2f), // Bayangan abu-abu terang
                    spotColor = Color.Gray.copy(alpha = 0.2f)
                )
                .height(screenHeight * 0.25f)
                .width(screenWidth * 0.5f)
                .zIndex(1f) // Memastikan di atas elemen lain
        )

        {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .border(
                        width = 1.dp,
                        color = Color.White
                    )
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom,

                ) {
                Image(
                    painter = painterResource(R.drawable.contoh_tanaman),
                    contentDescription = null,
                    modifier = Modifier.size(height = screenHeight * 0.15f,width= screenWidth * 0.30f ))

                Text(text = store.nama,
                    color = Color.Black,
                    fontSize = 18.sp,
                    textAlign = TextAlign.Start,
                    fontFamily = FontFamily.SansSerif)
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = store.price,
                    color = Color.Gray,
                    fontSize = 16.sp,
                    textAlign = TextAlign.End,
                    fontFamily = FontFamily.Default,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.End)
                        .padding(end = 8.dp)
                    )
            }
        }
    }
}


// Fungsi untuk menampilkan grid dari PlantCard
@Composable
fun StoreGrid(stores: List<Store>, screenHeight: Dp, screenWidth: Dp) {

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Menampilkan 2 kolom
        contentPadding = PaddingValues(8.dp)
    ) {
        items(stores) { store ->
            StoreCard(store = store, screenHeight, screenWidth)
        }

        item {

            // Menambahkan ruang ekstra setelah grid
            Spacer(modifier = Modifier.height(100.dp))  // Ini memberikan ruang ekstra di bagian bawah
        }
    }


}
