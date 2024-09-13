package com.example.leaflove

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.blur
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
fun registerScreen(navHost: NavHostController) {
    val image = painterResource(R.drawable.test)
    val image2 = painterResource(R.drawable.test2)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var phone_num by remember { mutableStateOf("") }



    // Buat background foto
    Box(
        modifier = Modifier
            .offset(y = 600.dp)
            .height(408.dp)
            .width(205.dp)
    ) {
        Image(painter = image, contentDescription = "", modifier = Modifier
            .fillMaxSize()
            .blur(10.dp))
    }
    Box(
        modifier = Modifier
            .offset(x = 216.dp)
            .height(408.dp)
            .width(232.dp)
    ) {
        Image(painter = image2, contentDescription = "", modifier = Modifier
            .fillMaxSize()
            .blur(10.dp))
    }


    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.padding(48.dp))
        Text(fontWeight = FontWeight.Bold, text = "Welcome to LeafLove", fontSize = 24.sp)
        Spacer(modifier = Modifier.padding(50.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
            .background(color = Background, shape = RoundedCornerShape(50)),
            colors = OutlinedTextFieldDefaults.colors(),
            shape = RoundedCornerShape(50),
            value = username,
            onValueChange = { username = it },
            label = {
                Text(
                    text = "Username"
                )
            })
        Spacer(modifier = Modifier.padding(1.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(color = Background, shape = RoundedCornerShape(50)),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(),
            value = password,
            onValueChange = { password = it },
            label = {
                Text(text = "Password")
            })
        Spacer(modifier = Modifier.padding(1.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(color = Background, shape = RoundedCornerShape(50)),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(),
            value = email,
            onValueChange = { email = it },
            label = {
                Text(text = "Email")
            })
        Spacer(modifier = Modifier.padding(1.dp))
        OutlinedTextField(modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .background(color = Background, shape = RoundedCornerShape(50)),
            shape = RoundedCornerShape(50),
            colors = OutlinedTextFieldDefaults.colors(),
            value = phone_num,
            onValueChange = { phone_num = it },
            label = {
                Text(text = "Phone Number")
            })
        Button(
            onClick = {navHost.navigate("mainscreen")}, modifier = Modifier
                .fillMaxWidth()
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



