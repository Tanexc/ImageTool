package ru.tanexc.imagetool

import androidx.compose.ui.graphics.ImageBitmap

expect fun ByteArray.toImageBitmap(): ImageBitmap

expect fun saveToCache(byteArray: ByteArray, url: String, compression: Int)

expect fun getFromCache(url: String): ByteArray?