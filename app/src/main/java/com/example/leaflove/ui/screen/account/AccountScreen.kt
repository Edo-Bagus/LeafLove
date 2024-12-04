package com.example.leaflove.ui.screen.account

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.zIndex
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.leaflove.R
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.AuthViewModel
import org.koin.compose.koinInject
import uploadImageToCloudinary

@Composable
fun AccountScreen(navHost: NavHostController) {
    val image1 = painterResource(R.drawable.ilham)
    val authViewModel = koinInject<AuthViewModel>()
    val context = LocalContext.current

    var imageUri by remember { mutableStateOf<Uri?>(null) } // Nullable URI to handle default case
    var customFont = FontFamily(
        Font(R.font.baloo_font, weight = FontWeight.Normal),
        Font(R.font.baloo_bold, weight = FontWeight.Bold)
    )

    // Image picker launcher
    val getImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imageUri = uri
            uploadImageProfileToCloudinary(context, uri, { url ->
                authViewModel.updateUserImageUrl(
                    url,
                    onSuccess = {
                        Toast.makeText(context, "Upload Successful", Toast.LENGTH_SHORT).show()
                    },
                    onFailure = {
                        Toast.makeText(context, "Upload Failed", Toast.LENGTH_SHORT).show()
                    }
                )
            })
        }
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val screenHeight = maxHeight
        val screenWidth = maxWidth

        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Top section with user data
            Box(
                modifier = Modifier
                    .height(screenHeight * 0.25f)
                    .fillMaxWidth()
                    .background(BasicGreen)
            ) {
                Column(
                    modifier = Modifier
                        .padding(screenWidth * 0.05f, screenHeight * 0.08f)
                ) {
                    Text(
                        text = authViewModel.userData.value?.username ?: "Username",
                        fontFamily = customFont,
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp,
                        color = Color.White,
                        textAlign = TextAlign.Left,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plant: ${authViewModel.userData.value?.my_plants?.size ?: 0}",
                        fontWeight = FontWeight.Bold,
                        fontFamily = customFont,
                        fontSize = 24.sp,
                        color = Color.White,
                        textAlign = TextAlign.Left
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Profile picture and information section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile picture button
                Button(
                    onClick = { getImageLauncher.launch("image/*") },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color.Transparent

                    ),
                    elevation = null, // Remove button elevation for a flat design
                    modifier = Modifier
                        .size(100.dp) // Ensure consistent size for the button
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize() // Make the image fill the button
                            .clip(RoundedCornerShape(50.dp)) // Circular shape matching the button size
                    ) {
                        val profileImageUrl = authViewModel.userData.value?.profile_image_url

                         if (!profileImageUrl.isNullOrEmpty()) {
                            // Display the profile image from the backend
                            Image(
                                painter = rememberAsyncImagePainter(profileImageUrl),
                                contentDescription = "Profile Image from Backend",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize() // Fill the box
                            )
                        } else {
                            // Fallback to the default image
                            Image(
                                painter = image1,
                                contentDescription = "Default Profile Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize() // Fill the box
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = authViewModel.userData.value?.username ?: "Bapak Budi",
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.Black,
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Plants: ${authViewModel.userData.value?.my_plants?.size ?: 0}",
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        color = Color.DarkGray,
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // About Us and Logout buttons
            Box(
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.1f)
                    .clickable { navHost.navigate("about") }
            ) {
                Text(
                    text = "About Us",
                    fontFamily = customFont,
                    fontSize = 24.sp
                )
            }

            HorizontalDivider(
                modifier = Modifier.padding(
                    top = 8.dp,
                    start = screenWidth * 0.05f,
                    end = screenWidth * 0.05f
                ),
                thickness = 2.dp,
                color = Color.LightGray
            )

            Box(
                modifier = Modifier
                    .padding(horizontal = screenWidth * 0.1f)
                    .clickable {
                        authViewModel.signout()
                    }
            ) {
                Text(
                    text = "Log Out",
                    fontFamily = customFont,
                    fontSize = 24.sp,
                    color = Color.Red
                )
            }
        }
    }
}


fun uploadImageProfileToCloudinary(context: Context, imageUri: Uri, onUploadSuccess: (String) -> Unit) {
    MediaManager.get().upload(imageUri)
        .unsigned("leaflove_image") // Replace with your unsigned preset
        .callback(object : UploadCallback {
            override fun onStart(requestId: String?) {
                Toast.makeText(context, "Upload started", Toast.LENGTH_SHORT).show()
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                val progress = (bytes / totalBytes.toDouble() * 100).toInt()
                Toast.makeText(context, "Uploading: $progress%", Toast.LENGTH_SHORT).show()
            }

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                val uploadedUrl = resultData?.get("secure_url") as String
                Toast.makeText(context, "Upload successful: $uploadedUrl", Toast.LENGTH_LONG).show()
                onUploadSuccess(uploadedUrl)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Toast.makeText(context, "Upload rescheduled: ${error.toString()}", Toast.LENGTH_LONG).show()
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                Toast.makeText(context, "Upload rescheduled: ${error.toString()}", Toast.LENGTH_LONG).show()

            }


        })
        .dispatch()
}




