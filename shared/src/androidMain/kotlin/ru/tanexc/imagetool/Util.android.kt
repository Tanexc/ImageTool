package ru.tanexc.imagetool

import android.content.Context
import android.graphics.Bitmap.CompressFormat
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.asImageBitmap
import org.koin.mp.KoinPlatform.getKoin
import java.io.ByteArrayOutputStream
import java.io.File

actual fun ByteArray.toImageBitmap(): ImageBitmap =
    BitmapFactory.decodeByteArray(this, 0, size).asImageBitmap()

@OptIn(ExperimentalStdlibApi::class)
actual fun saveToCache(byteArray: ByteArray, url: String, compression: Int) {
    val context: Context by getKoin().inject()
    val cache = context.cacheDir
    val compressed = ByteArrayOutputStream()
    byteArray.toImageBitmap().asAndroidBitmap().compress(CompressFormat.JPEG, compression, compressed)
    val file = File(cache, url.toByteArray().toHexString())
    file.writeBytes(compressed.toByteArray())
}

@OptIn(ExperimentalStdlibApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
actual fun getFromCache(url: String): ByteArray? {
    val context: Context by getKoin().inject()
    return runCatching {
        context.cacheDir.resolve(url.toByteArray().toHexString()).readBytes()
    }.getOrNull()
}