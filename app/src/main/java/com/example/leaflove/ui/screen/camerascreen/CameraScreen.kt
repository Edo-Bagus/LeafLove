import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.leaflove.ui.components.Plant
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalPermissionsApi::class)  // Required for permissions API
@Composable
fun CameraPermissionScreen(navHost: NavHostController) {
    // State to manage camera permission
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    if (cameraPermissionState.status.isGranted) {
        // If permission is granted, show the camera screen
        CameraScreen(navHost)
    } else {
        // If permission is not granted, show a button to request permission
        Button(onClick = { cameraPermissionState.launchPermissionRequest() }) {
            Text("Request Camera Permission")
        }
    }
}

@Composable
fun CameraScreen(navHost: NavHostController) {
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val imageCapture = remember { ImageCapture.Builder().build() }
    var savedImageUri by remember { mutableStateOf<Uri?>(null) }
    val coroutineScope = rememberCoroutineScope()
    val plantViewModel:PlantViewModel = koinInject()

    // Initialize CameraX when the lifecycle owner is ready
    LaunchedEffect(lifecycleOwner) {
        val provider = ProcessCameraProvider.getInstance(context).get()
        cameraProvider = provider
    }

    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview
        cameraProvider?.let {
            CameraPreview(
                cameraProvider = it,
                lifecycleOwner = lifecycleOwner,
                imageCapture = imageCapture
            )
        }

        // Capture and Upload Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 100.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Button(
                onClick =  {
                    saveImage(context, imageCapture) { uri ->
                        savedImageUri = uri
                        Toast.makeText(context, "Image saved: $uri", Toast.LENGTH_SHORT).show()

                        // Upload the image to Cloudinary
                        uri?.let { fileUri ->
                            coroutineScope.launch(Dispatchers.IO) {
                                Log.d("testfoto", "test")
                                uploadImageToCloudinary(context, fileUri) { uploadedUrl ->
                                    // Update the state with the uploaded URL
                                    plantViewModel.setImageUploadUrl(uploadedUrl)
                                    Log.d("Cloudinary", "Uploaded Image URL: $uploadedUrl")
                                    // You can pass this URL around or use it in your app
                                }                            }
                        }
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Take & Upload Picture")
            }
        }
    }
}

@Composable
fun CameraPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner,
    imageCapture: ImageCapture
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // Set up CameraX Preview
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview, imageCapture
            )

            val previewView = PreviewView(context)
            preview.setSurfaceProvider(previewView.surfaceProvider)
            previewView
        }
    )
}

fun saveImage(
    context: android.content.Context,
    imageCapture: ImageCapture,
    onImageSaved: (Uri) -> Unit
) {
    // File to save the image
    val outputDirectory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
    val file = File(
        outputDirectory,
        "${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())}.jpg"
    )

    val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()

    imageCapture.takePicture(
        outputOptions,
        ContextCompat.getMainExecutor(context),
        object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                onImageSaved(Uri.fromFile(file))
            }

            override fun onError(exception: ImageCaptureException) {
                Toast.makeText(context, "Error saving image: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }
    )
}


fun uploadImageToCloudinary(context: Context, imageUri: Uri, onUploadSuccess: (String) -> Unit) {
    MediaManager.get().upload(imageUri)
        .unsigned("leaflove_image") // Replace with your unsigned preset
        .callback(object :UploadCallback {
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