package eu.xap3y.objects

import java.io.File

/**
 * Data class to customize Texter manager
 */
data class TexterObj(
    val prefix: String = "",
    val debug: Boolean = false,
    val debugFile: File? = null
)
