package ru.tanexc.imagetool.widgets

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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import ru.tanexc.imagetool.CacheQuality
import ru.tanexc.imagetool.ImageState
import ru.tanexc.imagetool.ImageTool
import ru.tanexc.imagetool.ImageToolFactory

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
    onLoading: @Composable BoxScope.(progress: Float) -> Unit,
    onError: @Composable BoxScope.() -> Unit,
) {
    val imageTool: ImageTool = remember { ImageToolFactory.get() }
    val imageState: MutableState<ImageState> = remember { mutableStateOf(ImageState.Loading(0f)) }

    Box {
        LaunchedEffect(true) {
            CoroutineScope(
                Dispatchers.IO +
                        CoroutineExceptionHandler { _, _ -> imageState.value = ImageState.Failed }
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

        when (imageState.value) {
            is ImageState.Loading -> onLoading((imageState.value as ImageState.Loading).progress)
            is ImageState.Failed -> onError()
            is ImageState.Success ->
                Image(
                    bitmap = (imageState.value as ImageState.Success).imageBitmap,
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