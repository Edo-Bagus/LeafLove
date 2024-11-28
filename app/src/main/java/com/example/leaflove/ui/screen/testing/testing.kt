package com.example.leaflove.ui.screen.testing

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.leaflove.data.models.MyPlantDetailModel
import com.example.leaflove.data.models.MyPlantModel
import com.example.leaflove.data.models.UserDataModel
import com.example.leaflove.data.repositories.PlantRepository
import com.example.leaflove.viewmodel.AuthViewModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.coroutineScope
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

val db: FirebaseFirestore = FirebaseFirestore.getInstance()





@Composable
fun TestingScreen(navController: NavHostController) {
    val authViewModel: AuthViewModel = koinInject()
    val plantRepository: PlantRepository = koinInject()

    // State to hold the plant detail model
    var myPlantDetailModel by remember { mutableStateOf<MyPlantDetailModel?>(null) }

    // Observe user data from the ViewModel
    val userData = authViewModel.userData

    // Coroutine scope for launching the data fetch
    val coroutineScope = rememberCoroutineScope()

    // Launching the coroutine to fetch data when userData changes
    LaunchedEffect(userData.value) {
        userData.value?.my_plants?.let { userPlants ->
            if (userPlants.size > 1) {
                val plantDetail = plantRepository.getPlantDetails(userPlants[1].plant_fk)
                myPlantDetailModel = MyPlantDetailModel(userPlants[1], plantDetail)
            }
        }
    }

    Column {
        Spacer(modifier = Modifier.padding(100.dp))

        // Check if myPlantDetailModel is not null before accessing its properties
        myPlantDetailModel?.let { detailModel ->
            detailModel.plantDetailEntity.common_name?.let { Text(text = it) }
            Spacer(modifier = Modifier.padding(50.dp))
            detailModel.myPlantModel.plant_name.let { Text(text = it) }
        } ?: run {
            // Optionally show a loading or error state
            Text(text = "Loading...")
        }
    }
}
