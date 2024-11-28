package com.example.leaflove.ui.screen.bottomNav

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.leaflove.ui.components.ModelARCard
import com.example.leaflove.ui.theme.BasicGreen
import com.example.leaflove.viewmodel.PlantViewModel
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView


@SuppressLint("UnusedBoxWithConstraintsScope")
@Composable
fun ARScreen(plantViewModel: PlantViewModel) {
    val plants = plantViewModel.plantList.value

    BoxWithConstraints (modifier = Modifier.fillMaxSize()){
        val screenheight = maxHeight
        val screenwidth = maxWidth
        Box(
            modifier = Modifier
                .height(screenheight * 0.8f)
                .offset(y = screenheight * 0.08f),
        ) {
            val engine = rememberEngine()
            val modelLoader = rememberModelLoader(engine)
            val materialLoader = rememberMaterialLoader(engine)
            val cameraNode = rememberARCameraNode(engine)
            val childNodes = rememberNodes()
            val view = rememberView(engine)
            val collisionSystem = rememberCollisionSystem(view)

            var planeRenderer by remember { mutableStateOf(true) }
            var trackingFailureReason by remember {
                mutableStateOf<TrackingFailureReason?>(null)
            }
            var frame by remember { mutableStateOf<Frame?>(null) }
            var modelPlaced by remember { mutableStateOf(false) }
            var selectedModel by remember { mutableStateOf("") }
            var selectedModelName by remember { mutableStateOf("") }// No model selected initially

            val context = LocalContext.current
            val modelFiles = remember {
                // Use context.assets to list the files in the "models" folder
                context.assets.list("models")?.filter { it.endsWith(".glb") }?.map { "models/$it" } ?: emptyList()
            }

            ARScene(
                modifier = Modifier.fillMaxSize(),
                childNodes = childNodes,
                engine = engine,
                view = view,
                modelLoader = modelLoader,
                collisionSystem = collisionSystem,
                sessionConfiguration = { session, config ->
                    config.depthMode =
                        when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                            true -> Config.DepthMode.AUTOMATIC
                            else -> Config.DepthMode.DISABLED
                        }
                    config.instantPlacementMode = Config.InstantPlacementMode.LOCAL_Y_UP
                    config.lightEstimationMode =
                        Config.LightEstimationMode.ENVIRONMENTAL_HDR
                },
                cameraNode = cameraNode,
                planeRenderer = planeRenderer,
                onTrackingFailureChanged = {
                    trackingFailureReason = it
                },
                onSessionUpdated = { session, updatedFrame ->
                    frame = updatedFrame
                },
                onGestureListener = rememberOnGestureListener(
                    onSingleTapConfirmed = { motionEvent, node ->
                        // Ensure a model is selected before allowing placement
                        if (node == null && !modelPlaced && selectedModel.isNotEmpty()) {
                            val hitResults = frame?.hitTest(motionEvent.x, motionEvent.y)
                            hitResults?.firstOrNull {
                                it.isValid(
                                    depthPoint = false,
                                    point = false
                                )
                            }?.createAnchorOrNull()
                                ?.let { anchor ->
                                    planeRenderer = false
                                    childNodes += createAnchorNode(
                                        engine = engine,
                                        modelLoader = modelLoader,
                                        materialLoader = materialLoader,
                                        anchor = anchor,
                                        selectedModel = selectedModel // Pass the selected model
                                    )
                                    modelPlaced = true // Mark the model as placed
                                }
                        }
                    })
            )

            var expanded by remember { mutableStateOf(false) }

            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                contentPadding = PaddingValues(
                    start = screenwidth / 2 - (screenheight * 0.1f / 2), // Adjust this based on your card width
                    end = 16.dp,
                    top = 8.dp,
                    bottom = 8.dp
                ),
                modifier = Modifier
                    .height(screenheight * 0.15f)
                    .offset(y=screenheight * 0.63f)
            ) {
                items(plants.size) { index ->
                    val plant = plants[index]
                    ModelARCard(
                        encyclo = plant,
                        screenHeight = screenheight,
                        screenWidth = screenwidth,
                        onClick = {
                            selectedModel = "models/" + plant.id + ".glb"
                            selectedModelName = plant.common_name.toString()
                        })
                    Spacer(modifier = Modifier.width(screenwidth * 0.4f))
                }
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.TopCenter)
                    .padding(
                        top = 32.dp, // Adjust padding to account for both header and dropdown
                        start = 32.dp,
                        end = 32.dp
                    ),
                textAlign = TextAlign.Center,
                fontSize = 24.sp,
                color = Color.White,
                text = trackingFailureReason?.let {
                    it.getDescription(LocalContext.current)
                } ?: if (childNodes.isEmpty()) {
                    if (selectedModel.isEmpty()) {
                        "Select a model to begin"
                    } else {
                        "Model: ${selectedModelName} \n Point your phone down at an empty space, and move it around slowly"
                    }
                } else {
                    "Tap anywhere to add model"
                }
            )



            // Add a button to remove the placed model
            if (modelPlaced) {
                Button(
                    modifier = Modifier
                        .offset(x = screenwidth * 0.3f, y = screenheight * 0.57f),
                    colors = ButtonColors(
                        contentColor = Color.White,
                        containerColor = Color.Red,
                        disabledContentColor = BasicGreen,
                        disabledContainerColor = Color.White
                    ),
                    onClick = {
                        childNodes.clear() // Remove all child nodes (models)
                        planeRenderer = true // Re-enable plane renderer for placement
                        modelPlaced = false // Reset placement status
                    }
                ) {
                    Text(text = "Remove Model")
                }
            }
        }
    }
}

// Updated function to accept the selected model
fun createAnchorNode(
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    anchor: Anchor,
    selectedModel: String
): AnchorNode {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)
    Log.d("Cek Model", selectedModel)
    val modelNode = ModelNode(
        modelInstance = modelLoader.createModelInstance(selectedModel),
        // Scale to fit in a 0.5 meters cube
        scaleToUnits = 0.5f
    ).apply {
        // Model Node needs to be editable for independent rotation from the anchor rotation
        isEditable = true
        editableScaleRange = 0.2f..0.75f
    }
    val boundingBoxNode = CubeNode(
        engine,
        size = modelNode.extents,
        center = modelNode.center,
        materialInstance = materialLoader.createColorInstance(Color.White.copy(alpha = 0.5f))
    ).apply {
        isVisible = false
    }
    modelNode.addChildNode(boundingBoxNode)
    anchorNode.addChildNode(modelNode)

    listOf(modelNode, anchorNode).forEach {
        it.onEditingChanged = { editingTransforms ->
            boundingBoxNode.isVisible = editingTransforms.isNotEmpty()
        }
    }
    return anchorNode
}



