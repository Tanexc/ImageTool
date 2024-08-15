package com.tanexc.imagetool

internal object ImageToolFactory {
    private lateinit var imageTool: ImageTool

    fun get(): ImageTool {
        if (!ImageToolFactory::imageTool.isInitialized) {
            imageTool = ImageTool()
        }
        return imageTool
    }
}