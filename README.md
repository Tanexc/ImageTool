# 🖼️ ImageTool
<p align="center"> <b>ImageTool</b> is a Kotlin multiplatform library that provides the ability to download and cache images from network, storage and etc. Now library available on Android and IOS platforms</p>

<div align="center">
  <a><img src="https://img.shields.io/badge/Android-green?style=for-the-badge&logo=android&logoColor=white&color=00a500"></a>
  <a><img src="https://img.shields.io/badge/Ios-purple?style=for-the-badge&logo=apple&color=a586cc"></a>
</div>

# Implementation
![GitHub Release](https://img.shields.io/github/v/release/tanexc/imageTool?sort=date&style=flat-square&label=version)

For `Kotlin DSL` :
``` Kotlin
  implementation("io.github.tanexc:imagetool:[version]")
```

For `Groovy DSL` :
``` Kotlin
  implementation "io.github.tanexc:imagetool:[version]"
```

For `libs.version.toml` :

Add ```implementation(libs.imagetool)``` to build.gradle.kts and
``` toml

  [versions]

  imagetool-version="version"

  #...

  [libraries]

  #...

  imagetool = { module = "io.github.tanexc:imagetool", version.ref = "imagetool-version" }

```

# Usage

At this moment available just one widget to use:

``` Kotlin
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
)
```

`model` - String URL to get image from network

`contentDescription` - Description of the image

`modififer` - Modifier that will be applied to box where image lies

`alignment` - Optional alignment parameter used to place the `ImageBitmap` in the given bounds defined by the width and height

`contentScale` - Represents a rule to apply to scale a source image to be inscribed into the box

`alpha` - Optional opacity to be applied to the ImageBitmap when it is rendered on screen

`colorFilter` - Optional ColorFilter to apply for the image when it is rendered

`filterQuality` - Sampling algorithm applied to the bitmap when it is scaled and drawn into the destination. The default is FilterQuality. Low which scales using a bilinear sampling algorithm

`cacheQuality` - Optional cache quality parameter used to set quality of cached image. Can be `CacheQuality.NoCaching` that says image would not be cached

`onLoading` - Composable that will be displayed on loading. Receives float progress from 0.0 to 1.0

`onError` - Composable that will be displayed when loading failed. Receives `Throwable` that represents `Exception` has been caught

# In future
<ul>
  <li>Add more type to use as model for image</li>
  <li>Add more target platfroms</li>
</ul>

# Tech stack
- [Kotlin Multiplatform](https://kotlinlang.org/) and [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) based 

- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous work

- [Coin](https://insert-koin.io/) for dependency injection

- [Ktor](https://github.com/square/retrofit) for work with network

- [Gradle Maven publish plugin by vanniktech](https://github.com/vanniktech/gradle-maven-publish-plugin) is the amazing plugin for easy publishing to maven central

# Have suggestions or questions?
Contact me here:

<a href="https://t.me/tanexc"><img src="https://img.shields.io/static/v1?style=for-the-badge&message=Telegram&color=26A5E4&logo=Telegram&logoColor=FFFFFF&label="/></a> 
<a href="https://wa.me/qr/FR6RE7QOKFS6A1"><img src="https://img.shields.io/badge/Whatsapp-green?logo=whatsapp&logoColor=white&style=for-the-badge"/></a>
<a href="https://vk.com/tanexc"><img src="https://img.shields.io/badge/VKontakte-lightblue?style=for-the-badge&logo=vk&color=0f93ff&link=https%3A%2F%2Fvk.com%2Ftanexc"/></a>


# Find this repository useful? :heart:
Support it by joining __[stargazers](https://github.com/tanexc/imagetool/stargazers)__ for this repository. :star: <br>
And __[follow](https://github.com/tanexc)__ me!

# License
```xml
Designed and developed by 2024 Tanexc

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
