package eu.xap3y.xalib.objects

import org.jetbrains.annotations.Nullable
import java.io.File

/**
 * Data class to customize Texter manager
 */
data class TexterObj(
    val prefix: String = "",
    val debug: Boolean = false,
    @Nullable val debugFile: File? = null
)
