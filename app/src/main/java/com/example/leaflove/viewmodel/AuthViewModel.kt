package com.example.leaflove.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leaflove.data.models.MyPlantModel
import com.example.leaflove.data.models.UserDataModel
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.rpc.context.AttributeContext.Auth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private val _authState = mutableStateOf<AuthState>(AuthState.Unauthenticated)
    val authState: MutableState<AuthState> = _authState

    private val _userData = mutableStateOf<UserDataModel?>(null)
    val userData: MutableState<UserDataModel?> = _userData

    private val _selectedPlant = mutableStateOf<MyPlantModel?>(null)
    val selectedPlant: MutableState<MyPlantModel?> = _selectedPlant

    fun checkAuthStatus() {
        if (auth.currentUser == null) {
            _authState.value = AuthState.Unauthenticated
        } else {
            _authState.value = AuthState.Authenticated
            fetchUserData(auth.currentUser?.email ?: "")
        }
    }

    fun login(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password is empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    fetchUserData(email)
                    Log.d("Firestore", "User data fetched: ${_userData.value}")
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or password is empty")
            return
        }
        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    saveUserToDatabase(email, password) // Save user data to Firestore
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
        _userData.value = null // Clear user data on sign out
    }

    private fun saveUserToDatabase(email: String, password: String) {
        val userData = UserDataModel(email, password, mutableListOf())
        db.collection("users")
            .document(email) // Use email as the document ID
            .set(userData)
            .addOnSuccessListener {
                _userData.value = userData
            }
            .addOnFailureListener { exception ->
                _authState.value = AuthState.Error("Failed to save user data: ${exception.message}")
            }
    }

    private fun fetchUserData(email: String) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    for (document in querySnapshot) {
                        // Convert document to UserDataModel
                        val userData = document.toObject(UserDataModel::class.java)

                        // Manually handle the `my_plants` field if needed
                        val myPlantsList = mutableListOf<MyPlantModel>()
                        val plants = document.get("my_plants") as? List<Map<String, Any>>
                        Log.d("Firestore", "Plants: $plants")
                        plants?.forEach { plantMap ->
                            val plant = MyPlantModel(
                                plant_name = plantMap["plant_name"] as? String ?: "",
                                plant_age = plantMap["plant_age"] as? Timestamp ?: Timestamp.now(),
                                plant_last_watered = plantMap["plant_last_watered"] as? Timestamp ?: Timestamp.now(),
                                plant_to_be_watered = plantMap["plant_to_be_watered"] as? Timestamp ?: Timestamp.now(),
                                plant_fk = (plantMap["plant_fk"] as? Number)?.toInt() ?: 1,
                                plant_status = (plantMap["plant_status"] as? Number)?.toInt() ?: 1
                            )
                            myPlantsList.add(plant)
                        }

                        userData.my_plants = myPlantsList // Assign processed list to userData
                        Log.d("Firestore", "User data fetched: $userData")
                        _userData.value = userData
                        Log.d("Firestore Again", _userData.value.toString())
                        Log.d("Fireauth", _authState.value.toString())
                    }
                } else {
                    Log.d("Firestore", "No user found with the given email.")
                }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching user data", e)
            }
    }

    fun updateuserMyPlant(newMyPlant: MyPlantModel) {
        val currentUserData = _userData.value
        if (currentUserData != null) {
            // Create a new list to trigger recomposition
            val updatedPlants = currentUserData.my_plants.toMutableList().apply {
                add(newMyPlant)
            }
            _userData.value = currentUserData.copy(my_plants = updatedPlants)
            updatetoDatabase()
        } else {
            Log.e("Auth", "No user data available to update")
        }
    }

    private fun updatetoDatabase() {
        Log.d("Firestore", "Masuk")
        if (_authState.value is AuthState.Authenticated) {
            val currentUserData = _userData.value
            if (currentUserData != null) {
                val userEmail = currentUserData.email
                val updatedData = mapOf("my_plants" to currentUserData.my_plants)

                db.collection("users")
                    .document(userEmail) // Assuming email is the document ID
                    .update(updatedData)
                    .addOnSuccessListener {
                        Log.d("Firestore", "User data updated successfully!")
                    }
                    .addOnFailureListener { e ->
                        Log.e("Firestore", "Error updating user data", e)
                    }
            } else {
                Log.e("Firestore", "No user data to update")
            }
        } else {
            Log.e("Auth", "User is not authenticated")
        }
    }
}

sealed class AuthState {
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    data class Error(val message: String?) : AuthState()
}