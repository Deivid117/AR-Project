package com.dwh.arproject.ar_product.presentation

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.dwh.arproject.R
import com.dwh.arproject.core.presentation.ArScaffold
import com.dwh.arproject.product.domain.model.ArProductModel
import com.dwh.arproject.ui.theme.OrangeButton
import com.dwh.arproject.ui.theme.OrangeBorderSelectedModel
import com.dwh.arproject.ui.theme.OrangeCircularProgressIndicator
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import com.google.ar.core.TrackingState
import com.google.ar.core.exceptions.NotTrackingException
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.getDescription
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

@Composable
fun ArProductScreen(
    arProductModelS: List<ArProductModel>,
    modelName: String,
    onSetModelName: (String) -> Unit,
    onBackClick: () -> Unit
) {
    ArScaffold {
        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            ArProductView(
                backButtonModifier = Modifier.align(Alignment.TopStart),
                modelsSelectionModifier = Modifier.align(Alignment.BottomEnd),
                arProductModelS = arProductModelS,
                modelName = modelName,
                onSetModelName = onSetModelName,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
private fun ArProductView(
    backButtonModifier: Modifier = Modifier,
    modelsSelectionModifier: Modifier = Modifier,
    arProductModelS: List<ArProductModel>,
    modelName: String,
    onSetModelName: (String) -> Unit,
    onBackClick: () -> Unit
) {
    var selectedModel by remember { mutableStateOf(arProductModelS.first()) }
    val productHasManyModels = arProductModelS.size > 1
    
    ArSceneProducts(modelName = modelName, bottomPaddingButton = if (productHasManyModels) 150.dp else 50.dp)

    BackButton(modifier = backButtonModifier, onBackClick = onBackClick)

    ModelsSelection(
        modifier = modelsSelectionModifier,
        isVisible = productHasManyModels,
        arProductModels = arProductModelS,
        selectedModel = selectedModel
    ) {
        selectedModel = it
        onSetModelName(it.name)
    }
}

@Composable
private fun ArSceneProducts(modelName: String, bottomPaddingButton: Dp) {

    val context = LocalContext.current

    val engine = rememberEngine()
    val modelLoader = rememberModelLoader(engine)
    val materialLoader = rememberMaterialLoader(engine)
    val cameraNode = rememberARCameraNode(engine)
    val childNodes = rememberNodes()
    val view = rememberView(engine)
    val collisionSystem = rememberCollisionSystem(view)

    val planeRenderer by remember { mutableStateOf(true) }
    var isArSceneInitialized by remember { mutableStateOf(false) }

    val modelInstances = remember { mutableListOf<ModelInstance>() }

    var trackingFailureReason by remember { mutableStateOf<TrackingFailureReason?>(null) }
    var frame by remember { mutableStateOf<Frame?>(null) }
    var tempAnchorNode by remember { mutableStateOf<AnchorNode?>(null) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            childNodes = childNodes,
            engine = engine,
            view = view,
            modelLoader = modelLoader,
            collisionSystem = collisionSystem,
            sessionConfiguration = { session, config ->
                config.cloudAnchorMode = Config.CloudAnchorMode.DISABLED
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
            onSessionUpdated = { _, updatedFrame ->
                isArSceneInitialized = true
                frame = updatedFrame

                if (tempAnchorNode == null) {
                    updatedFrame.getUpdatedPlanes()
                        .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING && it.trackingState == TrackingState.TRACKING }
                        ?.let { plane ->
                            try {
                                if (plane.trackingState == TrackingState.TRACKING) {
                                    val anchor = plane.createAnchor(plane.centerPose)
                                    val (newAnchorNode, modelLoaded) = createAnchorNode(
                                        context = context,
                                        modelName = modelName,
                                        engine = engine,
                                        modelLoader = modelLoader,
                                        materialLoader = materialLoader,
                                        modelInstances = modelInstances,
                                        anchor = anchor
                                    )
                                    if (modelLoaded) {
                                        tempAnchorNode = newAnchorNode
                                        Log.d("ArView", "Model added. Current count: ${childNodes.size}")
                                    } else {
                                        Log.e("ModelError", "ModelNode is null. AnchorNode will not be added to childNodes.")
                                    }
                                } else {
                                    Log.e("AnchorCreation", "Plane is not being tracked properly.")
                                }
                            } catch (e: NotTrackingException) {
                                Log.e("AnchorCreation", "NotTrackingException: ARCore is not tracking properly.")
                            } catch (e: Throwable) {
                                Log.e("AnchorCreation", "Error creating anchor: ${e.message}", e)
                            }
                        }
                } else {
                    updatedFrame.getUpdatedPlanes()
                        .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }
                        ?.let { plane ->
                            if (plane.trackingState == TrackingState.TRACKING) {
                                val pose = plane.centerPose
                                tempAnchorNode?.anchor?.detach()
                                try {
                                    tempAnchorNode?.anchor = plane.createAnchor(pose)
                                } catch (e: NotTrackingException) {
                                    Log.e("AnchorCreation", "NotTrackingException: ARCore is not tracking properly.")
                                } catch (e: Throwable) {
                                    Log.e("AnchorCreation", "Error creating anchor: ${e.message}", e)
                                }
                            } else {
                                Log.e("AnchorUpdate", "Plane is not tracking. Anchor will not be updated.")
                            }
                        }
                }
            },
            onGestureListener = rememberOnGestureListener(
                onSingleTapConfirmed = { motionEvent, node ->
                    if (node == null && childNodes.size < 8) {
                        val hitResults = frame?.hitTest(motionEvent.x, motionEvent.y)
                        hitResults?.firstOrNull {
                            it.isValid(depthPoint = false, point = false)
                        }?.createAnchorOrNull()
                            ?.let { anchor ->
                                val (newAnchorNode, modelLoaded) = createAnchorNode(
                                    context = context,
                                    modelName = modelName,
                                    engine = engine,
                                    modelLoader = modelLoader,
                                    materialLoader = materialLoader,
                                    modelInstances = modelInstances,
                                    anchor = anchor
                                )

                                if (modelLoaded) {
                                    tempAnchorNode = newAnchorNode
                                    childNodes += newAnchorNode
                                    Log.d("ArView", "Model added. Current count: ${childNodes.size}")
                                } else {
                                    Log.e("ModelError", "ModelNode is null. AnchorNode will not be added to childNodes.")
                                }

                                tempAnchorNode = null
                            }
                    }
                }
            )
        )

        if (!isArSceneInitialized) {
            CircularProgressIndicator(
                modifier = Modifier.size(70.dp),
                color = OrangeCircularProgressIndicator,
                strokeWidth = 5.dp
            )
        }

        Text(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .padding(top = 60.dp, start = 2.dp, end = 2.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            lineHeight = 18.sp,
            text = trackingFailureReason?.getDescription(LocalContext.current) ?: if (childNodes.isEmpty()) {
                stringResource(R.string.point_your_phone_down)
            } else if (childNodes.size == 10) {
                stringResource(id = R.string.ar_product_limit_models)
            } else {
                stringResource(R.string.tap_anywhere_to_add_model)
            }
        )

        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = bottomPaddingButton),
            enter = fadeIn(),
            exit = fadeOut(),
            visible = childNodes.size > 0
        ) {
            Button(
                onClick = {
                    tempAnchorNode?.anchor?.detach()
                    tempAnchorNode = null
                    modelInstances.clear()
                    childNodes.clear()
                },
                border = BorderStroke(2.dp, Color.White),
                colors = ButtonDefaults.buttonColors(containerColor = OrangeButton)
            ) {
                Text(text = stringResource(id = R.string.ar_product_delete_models_btn), style = MaterialTheme.typography.labelLarge)
            }
        }
    }

    LaunchedEffect(modelName) {
        //tempAnchorNode?.anchor?.detach()
        tempAnchorNode = null
        modelInstances.clear()
    }

    DisposableEffect(Unit) {
        onDispose {
            //tempAnchorNode?.anchor?.detach()
            tempAnchorNode = null
            modelInstances.clear()
            childNodes.clear()
        }
    }
}

fun createAnchorNode(
    context: Context,
    modelName: String,
    engine: Engine,
    modelLoader: ModelLoader,
    materialLoader: MaterialLoader,
    modelInstances: MutableList<ModelInstance>,
    anchor: Anchor
): Pair<AnchorNode, Boolean> {
    val anchorNode = AnchorNode(engine = engine, anchor = anchor)

    val modelNode = try {
        if (modelInstances.isEmpty()) {
            modelInstances += modelLoader.createInstancedModel("models/$modelName.glb", 8)
        }
        ModelNode(
            modelInstance = modelInstances.removeLast(),
            scaleToUnits = 0.5f
        ).apply {
            isEditable = true
        }
    } catch (e: Exception) {
        Log.e("ModelLoadError", "Error loading model: ${e.message}")
        null
    }

    if (modelNode != null) {
        val boundingBoxNode = CubeNode(
            engine = engine,
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
        return Pair(anchorNode, true)

    } else {
        Toast.makeText(context, "Ocurrió un error al cargar el modelo", Toast.LENGTH_SHORT).show()
        Log.e("ModelError", "ModelNode is null. AnchorNode will be created without model.")
        return Pair(anchorNode, false)
    }
}


@Composable
private fun BackButton(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        IconButton(
            onClick = { onBackClick() },
            colors = IconButtonDefaults.iconButtonColors(containerColor = OrangeButton)
        ) {
            Icon(
                imageVector = Icons.Outlined.KeyboardArrowLeft,
                contentDescription = "back icon",
                tint = Color.White
            )
        }
    }
}

@Composable
private fun ModelsSelection(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    arProductModels: List<ArProductModel>,
    selectedModel: ArProductModel,
    onClickModel: (ArProductModel) -> Unit = {}
) {
    if (isVisible) {
        Column(modifier = modifier.fillMaxWidth()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    items = arProductModels,
                    key = { productModel -> productModel.id }
                ) { productModel ->
                    ModelItem(
                        productModel = productModel,
                        isSelected = selectedModel == productModel,
                        onClickModel = onClickModel
                    )
                }
            }
        }
    }
}

@Composable
private fun ModelItem(
    productModel: ArProductModel,
    isSelected: Boolean = false,
    onClickModel: (ArProductModel) -> Unit
) {
    val boxSize by
    animateDpAsState(targetValue = if (isSelected) 75.dp else 50.dp, label = "")
    val boxBorderColor by
    animateColorAsState(targetValue = if (isSelected) OrangeBorderSelectedModel else Color.LightGray, label = "")

    Box(
        modifier = Modifier
            .clip(CircleShape)
            .size(boxSize)
            .aspectRatio(1f)
            .border(
                width = if (isSelected) 3.dp else 1.dp,
                color = boxBorderColor,
                shape = CircleShape
            )
            .clickable { onClickModel(productModel) },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(5.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(productModel.image)
                .crossfade(500)
                .build(),
            contentDescription = "model image",
            placeholder = painterResource(id = R.drawable.ic_image),
            error = painterResource(id = R.drawable.ic_broken_image),
            contentScale = ContentScale.FillBounds,
        )
    }
}