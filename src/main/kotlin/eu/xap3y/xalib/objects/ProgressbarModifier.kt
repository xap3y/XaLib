package eu.xap3y.xalib.objects

data class ProgressbarModifier(
    val percentage: Int,
    val symbolFill: Char = '|',
    val symbolEmpty: Char = '|',
    val colorFill: String = "§a",
    val colorEmpty: String = "§7"
)
