import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)  // Required for permissions API
@Composable
fun CameraPermissionScreen(navHost:NavHostController) {
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
fun CameraScreen(navHost:NavHostController) {
    var cameraProvider by remember { mutableStateOf<ProcessCameraProvider?>(null) }

    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    // Initialize CameraX when the lifecycle owner is ready
    LaunchedEffect(lifecycleOwner) {
        val provider = ProcessCameraProvider.getInstance(context).get()
        cameraProvider = provider
    }

    // If CameraX is initialized, show the preview
    cameraProvider?.let {
        CameraPreview(cameraProvider = it, lifecycleOwner = lifecycleOwner)
    }

    // Capture button
    Button(onClick = {
        // Handle capturing an image here
        Toast.makeText(context, "Capture button clicked", Toast.LENGTH_SHORT).show()
    }) {
        Text("Take Picture")
    }
}

@Composable
fun CameraPreview(
    cameraProvider: ProcessCameraProvider,
    lifecycleOwner: LifecycleOwner
) {
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            // Set up CameraX Preview
            val preview = Preview.Builder().build()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val camera = cameraProvider.bindToLifecycle(
                lifecycleOwner, cameraSelector, preview
            )

            val previewView = PreviewView(context)
            preview.setSurfaceProvider(previewView.surfaceProvider)
            previewView
        }
    )
}
