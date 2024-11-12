package com.example.leaflove.ui.screen.loginscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leaflove.R
import com.example.leaflove.ui.theme.Background
import com.example.leaflove.ui.theme.ButtonGreen
import com.example.leaflove.viewmodel.AuthViewModel


@Composable
fun registerScreen(navHost: NavHostController, authViewModel: AuthViewModel) {
    val image = painterResource(R.drawable.backgroundlogin1)
    val image2 = painterResource(R.drawable.test2)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone_num by remember { mutableStateOf("") }
    var authState = authViewModel.authState.observeAsState()



    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        val isLandscape = screenWidth > screenHeight

        // Background image for responsiveness
        Box(
            modifier = Modifier
                .wrapContentSize()
                .offset(y = if (isLandscape) screenHeight * 0.5f else screenHeight * 0.69f)
                .blur(radius = 10.dp)
        ) {
            Image(painter = image, contentDescription = "",
                modifier = Modifier
                    .width(screenWidth * 0.4f)
                    .height(screenHeight * 0.38f)
            )
        }
        Box(
            modifier = Modifier
                .offset(x = screenWidth * 0.7f)
                .wrapContentSize()
                .blur(radius = 10.dp)
        ) {
            Image(
                painter = image2, contentDescription = "",
                modifier = Modifier
                    .width(screenWidth * 0.3f)
                    .height(screenHeight * 0.23f)
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.padding(48.dp))
            Text(
                fontWeight = FontWeight.Bold,
                text = "Welcome to LeafLove",
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.padding(50.dp))

            // Responsive text fields
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                colors = OutlinedTextFieldDefaults.colors(),
                shape = RoundedCornerShape(50),
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(),
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(),
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email") }
            )
            Spacer(modifier = Modifier.padding(8.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(horizontal = 15.dp)
                    .background(color = Background, shape = RoundedCornerShape(50)),
                shape = RoundedCornerShape(50),
                colors = OutlinedTextFieldDefaults.colors(),
                value = phone_num,
                onValueChange = { phone_num = it },
                label = { Text(text = "Phone Number") }
            )
            Button(
                onClick = { authViewModel.signup(email, password) },
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .padding(15.dp),
                colors = ButtonColors(
                    containerColor = ButtonGreen,
                    contentColor = Color.White,
                    disabledContainerColor = ButtonGreen,
                    disabledContentColor = Color.White
                )
            ) {
                Text(text = "Sign Up")
            }
        }
    }
}



