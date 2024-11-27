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
import com.example.leaflove.data.models.UserDataModel
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot
import org.koin.androidx.compose.koinViewModel

val db: FirebaseFirestore = FirebaseFirestore.getInstance()

fun fetchUserData(email: String, onResult: (UserDataModel?) -> Unit) {
    db.collection("users")
        .whereEqualTo("email", email) // Query by email
        .get()
        .addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (!task.result.isEmpty) {
                    val document = task.result.documents[0] // Get the first document
                    val userData = document.toObject(UserDataModel::class.java) // Map to UserDataModel
                    onResult(userData) // Pass the result to the callback
                } else {
                    onResult(null) // No matching user found
                }
            } else {
                onResult(null) // Query failed
            }
        }
}



@Composable
fun TestingScreen(navController: NavHostController) {
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    var userData by remember { mutableStateOf<UserDataModel?>(null) }
    var email by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Spacer(modifier = Modifier.padding(50.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Button to Fetch Data
        Button(onClick = {
            fetchUserData(email) { result ->
                userData = result
            }
        }) {
            Text(text = "Fetch User Data")
        }

        Spacer(modifier = Modifier.padding(8.dp))

        // Display User Data
        if (userData != null) {
            Text(text = "Email: ${userData?.email}")
            Text(text = "Password: ${userData?.password}")
            userData?.my_plants?.forEach { plant ->
                Text(text = "Plant Name: ${plant.plant_name}")
                Text(text = "Plant Age: ${plant.plant_age}")
                Text(text = "Last Watered: ${plant.plant_last_watered}")
                Text(text = "To Be Watered: ${plant.plant_to_be_watered}")
            }
        } else {
            Text(text = "No user data found or enter an email to fetch.")
        }
    }
}
