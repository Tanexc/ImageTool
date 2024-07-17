package ru.tanexc.imagetool

import androidx.compose.ui.graphics.ImageBitmap
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onDownload
import io.ktor.client.request.get

/**ImageTool used in widgets to access loading and caching feature
 */
internal class ImageTool {
    private val httpClient = HttpClient()


    /** @return [androidx.compose.ui.graphics.ImageBitmap]
     *
     * @param url URL to image in network
     * @param cacheQuality there you can pass values from [CacheQuality]
     * @param collector this value can be used to collect state of loading image by getting received part and total size
     */
    suspend fun loadImage(
        url: String,
        cacheQuality: CacheQuality = CacheQuality.NoCompressing,
        collector: (suspend (received: Long, total: Long) -> Unit)? = null
    ): ImageBitmap {
        var data: ByteArray? = getFromCache(url)
        if (data != null) {
            collector?.let { it(1, 1) }
            return data.toImageBitmap()
        } else {
            val response = httpClient.get(url) {
                onDownload(collector)
            }
            data = response.body<ByteArray>()
            if (cacheQuality != CacheQuality.NoCaching) {
                saveToCache(data, url, cacheQualityToInt(cacheQuality))
            }
            return data.toImageBitmap()
        }
    }
}
