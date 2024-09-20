package com.tanexc.imagetool.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

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
                    modifier,
                    alignment,
                    contentScale,
                    alpha,
                    colorFilter,
                    filterQuality
                )
        }
    }
}