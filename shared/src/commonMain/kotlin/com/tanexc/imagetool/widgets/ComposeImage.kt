package com.tanexc.imagetool.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.DefaultAlpha
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope.Companion.DefaultFilterQuality
import androidx.compose.ui.layout.ContentScale
import com.tanexc.imagetool.CacheQuality
import com.tanexc.imagetool.ImageState
import com.tanexc.imagetool.ImageTool
import com.tanexc.imagetool.ImageToolFactory
import com.tanexc.imagetool.toImageBitmap
import io.ktor.http.Url
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

/**
 * @param model String URL to get image from network
 * @param contentDescription Description of image
 * @param modifier Modifier that will be applied to box where image lies
 * @param alignment Optional alignment parameter used to place the [ImageBitmap] in the given bounds defined by the width and height
 * @param contentScale Represents a rule to apply to scale a source image to be inscribed into the box
 * @param alpha Optional opacity to be applied to the image when it is rendered onscreen
 * @param colorFilter Optional ColorFilter to apply for the image when it is rendered onscreen
 * @param filterQuality Sampling algorithm applied to the bitmap when it is scaled and drawn into the destination. The default is FilterQuality. Low which scales using a bilinear sampling algorithm
 * @param cacheQuality Optional cache quality parameter used to set quality of cached image. Can be [CacheQuality.NoCaching] that says image would not be cached
 * @param onLoading Composable that will be displayed on loading. Receives float progress from 0.0 to 1.0
 * @param onError Composable that will be displayed when loading failed. Receives [Throwable] that represents [Exception] has been caught
 */

@Composable
fun ComposeImage(
    model: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    cacheQuality: CacheQuality = CacheQuality.NoCompressing,
    onLoading: @Composable BoxScope.(progress: Float) -> Unit = {},
    onError: @Composable BoxScope.(throwable: Throwable) -> Unit = {},
) {
    val imageTool: ImageTool = remember { ImageToolFactory.get() }
    val imageState: MutableState<ImageState> = remember { mutableStateOf(ImageState.Loading(0f)) }

    LaunchedEffect(true) {
        CoroutineScope(
            Dispatchers.IO +
                    CoroutineExceptionHandler { _, throwable ->
                        imageState.value = ImageState.Failed(throwable)
                    }
        ).launch {
            val data = imageTool.loadImage(
                url = model,
                cacheQuality = cacheQuality,
                collector = { received, total ->
                    imageState.value = ImageState.Loading(received.toFloat() / total)
                })
            imageState.value = ImageState.Success(data)
        }
    }

    Box(modifier = modifier) {
        when (val state = imageState.value) {
            is ImageState.Loading -> onLoading(state.progress)
            is ImageState.Failed -> onError(state.throwable)
            is ImageState.Success ->
                Image(
                    bitmap = state.imageBitmap,
                    contentDescription,
                    Modifier.fillMaxSize(),
                    alignment,
                    contentScale,
                    alpha,
                    colorFilter,
                    filterQuality
                )
        }
    }
}


/**
 * @param model ByteArray that will be used to build the image
 * @param contentDescription Description of the image
 * @param modifier Modifier that will be applied to box where image lies
 * @param alignment Optional alignment parameter used to place the [ImageBitmap] in the given bounds defined by the width and height
 * @param contentScale Represents a rule to apply to scale a source image to be inscribed into the box
 * @param alpha Optional opacity to be applied to the image when it is rendered onscreen
 * @param colorFilter Optional ColorFilter to apply for the image when it is rendered on screen
 * @param filterQuality Sampling algorithm applied to the bitmap when it is scaled and drawn into the destination. The default is FilterQuality. Low which scales using a bilinear sampling algorithm
 * @param cacheQuality Optional cache quality parameter used to set quality of cached image. Can be [CacheQuality.NoCaching] that says image would not be cached
 * @param onLoading Composable that will be displayed on loading. Receives float progress from 0.0 to 1.0
 * @param onError Composable that will be displayed when loading failed. Receives [Throwable] that represents [Exception] has been caught
 */

@Composable
fun ComposeImage(
    model: ByteArray,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    alignment: Alignment = Alignment.Center,
    contentScale: ContentScale = ContentScale.Fit,
    alpha: Float = DefaultAlpha,
    colorFilter: ColorFilter? = null,
    filterQuality: FilterQuality = DefaultFilterQuality,
    cacheQuality: CacheQuality = CacheQuality.NoCompressing,
    onLoading: @Composable BoxScope.(progress: Float) -> Unit = {},
    onError: @Composable BoxScope.(throwable: Throwable) -> Unit = {},
) {
    val imageState: MutableState<ImageState> = remember { mutableStateOf(ImageState.Loading(0f)) }


    Box(modifier = modifier) {
        when (val state = imageState.value) {
            is ImageState.Loading -> onLoading(state.progress)
            is ImageState.Failed -> onError(state.throwable)
            is ImageState.Success ->
                Image(
                    bitmap = model.toImageBitmap(),
                    contentDescription,
                    Modifier.fillMaxSize(),
                    alignment,
                    contentScale,
                    alpha,
                    colorFilter,
                    filterQuality
                )
        }
    }
}