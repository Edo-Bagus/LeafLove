package com.example.leaflove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leaflove.ui.theme.Background
import com.example.leaflove.ui.theme.ButtonGreen


@Composable
fun loginScreen(navHost: NavHostController) {
    val image = painterResource(R.drawable.test)
    val image2 = painterResource(R.drawable.test2)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Responsive layout using BoxWithConstraints
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight



        // Adjust background images based on screen size
        Box(
            modifier = Modifier
                .offset(y = screenHeight * 0.8f) // Adjust Y offset dynamically
                .height(screenHeight * 0.3f) // Scale the height based on screen height
                .width(screenWidth * 0.4f)  // Scale the width based on screen width
        ) {
            Image(painter = image, contentDescription = "", modifier = Modifier.fillMaxSize())
        }
//        Box(
//            modifier = Modifier
//                .offset(x = screenWidth * 0.5f, y = screenHeight * 0.7f) // Adjust offset
//                .height(screenHeight * 0.3f)
//                .width(screenWidth * 0.4f)
//        ) {
//            Image(painter = image2, contentDescription = "", modifier = Modifier.fillMaxSize())
//        }

        // Main content area in Column
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),  // Add some horizontal padding
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center  // Center content vertically
        ) {
            Text(
                fontWeight = FontWeight.Bold,
                text = "Welcome to LeafLove",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(24.dp))  // Space between title and fields

            // Email input field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") },
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(16.dp))  // Space between email and password fields

            // Password input field
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") },
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors()
            )
            Spacer(modifier = Modifier.height(16.dp))  // Space between password and button

            // Sign In Button
            Button(
                onClick = { navHost.navigate("mainscreen") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonGreen,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Sign In")
            }

            Spacer(modifier = Modifier.height(8.dp))  // Space between buttons

            // Sign Up Button
            Button(
                onClick = { navHost.navigate("signupscreen") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = ButtonGreen
                )
            ) {
                Text(text = "Sign Up", textDecoration = TextDecoration.Underline)
            }
        }
    }
}




