package com.tanexc.imagetool

/**
 * CacheQuality represents cache quality option
 *
 * [NoCaching] - data will not be cached
 *
 * [NoCompressing] - 100% of quality
 *
 * [High] - 80% of quality
 *
 * [Medium] - 50% of quality
 *
 * [Low] - 30% of quality
 *
 * [Shakal] - 1% of quality
 */
enum class CacheQuality(val value: Int) {
    NoCaching(0),
    NoCompressing(100),
    High(80),
    Medium(50),
    Low(25),
    Shakal(1)
}