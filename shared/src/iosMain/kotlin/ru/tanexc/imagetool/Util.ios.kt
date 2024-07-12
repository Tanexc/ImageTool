package ru.tanexc.imagetool

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import io.ktor.utils.io.core.toByteArray
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.allocArrayOf
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image
import platform.Foundation.NSData
import platform.Foundation.NSTemporaryDirectory
import platform.Foundation.NSURL
import platform.Foundation.create
import platform.Foundation.dataWithContentsOfFile
import platform.Foundation.dataWithContentsOfURL
import platform.Foundation.writeToFile
import platform.Foundation.writeToURL
import platform.posix.memcpy

typealias ImageBytes = NSData

@OptIn(ExperimentalForeignApi::class)
fun ImageBytes.toByteArray(): ByteArray = ByteArray(this@toByteArray.length.toInt()).apply {
    usePinned {
        memcpy(it.addressOf(0), this@toByteArray.bytes, this@toByteArray.length)
    }
}

@OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
fun ByteArray.toImageBytes(): ImageBytes = memScoped {
    NSData.create(
        bytes = allocArrayOf(this@toImageBytes),
        length = this@toImageBytes.size.toULong()
    )
}

actual fun ByteArray.toImageBitmap(): ImageBitmap =
    Image.makeFromEncoded(this).toComposeImageBitmap()


@OptIn(ExperimentalStdlibApi::class)
actual fun saveToCache(byteArray: ByteArray, url: String, compression: Int) {
    val data = byteArray.toImageBytes()

    val tempDirectory = NSTemporaryDirectory()
    val dataPath = "$tempDirectory/${url.toByteArray().toHexString()}.dat"
    val dataUrl = NSURL.fileURLWithPath(dataPath)
    data.writeToURL(dataUrl, true)
}

@OptIn(ExperimentalStdlibApi::class)
actual fun getFromCache(url: String): ByteArray? {
    val tempDirectory = NSTemporaryDirectory()
    val dataPath = "$tempDirectory/${url.toByteArray().toHexString()}.dat"
    val dataUrl = NSURL.fileURLWithPath(dataPath)

    val data = ImageBytes.dataWithContentsOfURL(dataUrl)?.toByteArray()
    return data
}