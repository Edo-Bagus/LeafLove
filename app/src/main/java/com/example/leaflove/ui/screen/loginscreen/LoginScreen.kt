package com.example.leaflove.ui.screen.loginscreen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leaflove.R
import com.example.leaflove.ui.theme.Background
import com.example.leaflove.ui.theme.ButtonGreen
import com.example.leaflove.viewmodel.AuthState
import com.example.leaflove.viewmodel.AuthViewModel
import org.koin.compose.koinInject


@Composable
fun LoginScreen(navHost: NavHostController) {
    val authViewModel = koinInject<AuthViewModel>()

    val image = painterResource(R.drawable.backgroundlogin1)
    val image2 = painterResource(R.drawable.test2)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var isPasswordVisible by remember { mutableStateOf(false) }
    val authState = authViewModel.authState.value
    val focusManager = LocalFocusManager.current


    LaunchedEffect(authState, authViewModel.userData.value) {
        when (authState) {
            is AuthState.Authenticated -> {
                if (authViewModel.userData.value != null) {
                    navHost.navigate("mainscreen")
                }
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    authState.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> Unit
        }
    }

    // Responsive layout using BoxWithConstraints
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenWidth = maxWidth
        val screenHeight = maxHeight

        // Determine if the screen is in landscape or portrait orientation
        val isLandscape = screenWidth > screenHeight

        // Composable for the UI
        if (authState is AuthState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)), // Optional dimmed background
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = Color.White, // Customize as needed
                    strokeWidth = 4.dp
                )
            }
        }
        else
        {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .offset(y = if (isLandscape) screenHeight * 0.5f else screenHeight * 0.69f)
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
            ) {
                Image(
                    painter = image2, contentDescription = "",
                    modifier = Modifier
                        .width(screenWidth * 0.3f)
                        .height(screenHeight * 0.23f)
                )
            }

            // Main content area
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = screenWidth * 0.05f),  // Adjust padding relative to screen width
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    fontWeight = FontWeight.Bold,
                    text = "Welcome to LeafLove",
                    fontSize = if (isLandscape) 20.sp else 24.sp  // Adjust text size based on orientation
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Email input field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.05f) // Adjust padding dynamically
                        .background(color = Background, shape = RoundedCornerShape(50)),
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = "Email") },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password input field
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.05f)
                        .background(color = Background, shape = RoundedCornerShape(50)),
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = "Password") },
                    shape = RoundedCornerShape(50),
                    colors = OutlinedTextFieldDefaults.colors(),
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        val icon = if (isPasswordVisible) R.drawable.ic_visibility else R.drawable.ic_visibility_off
                        Image(
                            painter = painterResource(icon),
                            contentDescription = if (isPasswordVisible) "Hide password" else "Show password",
                            modifier = Modifier
                                .padding(8.dp)
                                .clickable { isPasswordVisible = !isPasswordVisible }
                        )
                    },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions.Default.copy(
                        imeAction = ImeAction.Done // Set IME action to Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            // Fungsi yang dijalankan saat Enter ditekan
                            authViewModel.login(email, password) // Menutup keyboard
                        }
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))


                Button(
                    onClick = {
                        authViewModel.login(email, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = screenWidth * 0.05f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonGreen,
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Sign In")
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Sign Up Button
                Button(
                    onClick = { navHost.navigate("signupscreen"){

                    } },
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
}





