package eu.xap3y.xalib.objects

import net.kyori.adventure.text.event.ClickEvent
import javax.annotation.Nullable

data class FormatterModifiers (
    @Nullable val clickAction: ClickEvent? = null,
    @Nullable val hoverText: String? = null
)