package com.tanexc.imagetool

/**
 * CacheQuality represents cache quality option
 *
 * [NoCaching] ~ data will not be cached
 *
 * [NoCompressing] ~ 100% of quality
 *
 * [High] ~ 80% of quality
 *
 * [Medium] ~ 50% of quality
 *
 * [Low] ~ 30% of quality
 *
 * [Shakal] ~ 1% of quality
 */
enum class CacheQuality {
    NoCaching, NoCompressing, High, Medium, Low, Shakal
}

internal fun cacheQualityToInt(q: CacheQuality): Int = when (q) {
    CacheQuality.Shakal -> 1
    CacheQuality.Low -> 30
    CacheQuality.Medium -> 50
    CacheQuality.High -> 80
    CacheQuality.NoCompressing -> 100
    else -> 0
}