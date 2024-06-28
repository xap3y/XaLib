@file:Suppress("DEPRECATION")

package eu.xap3y.xalib.managers

import eu.xap3y.xalib.enums.DefaultFontInfo
import eu.xap3y.xalib.objects.FormatterModifiers
import eu.xap3y.xalib.objects.ProgressbarModifier
import eu.xap3y.xalib.objects.TextModifier
import eu.xap3y.xalib.objects.TexterObj
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.event.HoverEvent
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import java.util.logging.Level

class Texter(private val data: TexterObj) {

    /**
     * Send a message to a CommandSender, can be a player
     *
     * @param p0 Message text receiver
     * @param text Message that will be sent to receiver
     * @param modifiers The text modifiers
     */
    fun response(p0: CommandSender, text: String, modifiers: TextModifier) {
        val textToSend: String = if (modifiers.colored) colored(text) else text
        val prefix: String = if (modifiers.withPrefix) colored(data.prefix) else ""
        p0.sendMessage("$prefix$textToSend")
    }

    /**
     * Send a message to a CommandSender, can be a player
     *
     * @param p0 Message text receiver
     * @param text Message that will be sent to receiver
     */
    fun response(p0: CommandSender, text: String) {
        response(p0, text, TextModifier())
    }

    /**
     * Log a text into the console
     *
     * @param text The text that will be logged
     * @param modifiers The text modifiers
     */
    fun console(text: String, modifiers: TextModifier) =
        response(Bukkit.getConsoleSender(), text, modifiers)

    /**
     * Log a text into the console
     *
     * @param text The text that will be logged
     * @param wPrefix Boolean If the text should have a prefix
     */
    fun console(text: String, wPrefix: Boolean) =
        response(Bukkit.getConsoleSender(), text, TextModifier(wPrefix))

    /**
     * Log a text into the console
     *
     * @param text The text that will be logged
     */
    fun console(text: String) =
        response(Bukkit.getConsoleSender(), text)

    /**
     * Log a text into debug file
     *
     * @param text The text that will be logged
     * @param level Java logging level
     * @return Success boolean
     */
    fun debugLog(text: String, level: Level): Boolean {
        if (!data.debug || data.debugFile == null) return false

        if (!data.debugFile.exists())
            try {
                data.debugFile.createNewFile()
            } catch (e: Exception) {
                return false
            }
        val levelName: String = level.name ?: ""
        val currentTime: String = java.text.SimpleDateFormat("HH:mm:ss").format(java.util.Date())
        val textToLog = "[$currentTime] [$levelName] $text\n"
        data.debugFile.appendText(textToLog)
        return true
    }

    companion object {

        /**
         * Convert a text into its colored form
         *
         * @param text Text that will be colored
         * @return Colored text
         */
        @Deprecated("Use § symbols in text Components and it will be automatically formatted", ReplaceWith("Component.text()", "net.kyori.adventure.text"))
        @JvmStatic
        fun colored(text: String): String =
            ChatColor.translateAlternateColorCodes('&', text)

        @JvmStatic
        fun progressBar(modifiers: ProgressbarModifier): String {
            val maxLength = 20
            val fillText: String = modifiers.symbolFill.toString().repeat((modifiers.percentage * maxLength) / 100)
            val emptyText: String = modifiers.symbolEmpty.toString().repeat(maxLength - fillText.length)
            return modifiers.colorFill + fillText + modifiers.colorEmpty +  emptyText
        }

        /**
         * Center a message for a chat
         *
         * @param message Message that will be centered
         * @return Centered message
         */
        @JvmStatic
        fun centered(message: String): String {
            var messagePxSize = 0
            var previousCode = false
            var isBold = false
            for (c: Char in message.toCharArray()) {
                if (c == '&') {
                    previousCode = true
                    continue
                } else if (previousCode) {
                    previousCode = false
                    if (c == 'l' || c == 'L') {
                        isBold = true
                        continue
                    } else isBold = false
                } else {
                    val dFI: DefaultFontInfo = DefaultFontInfo.getDefaultFontInfo(c)
                    messagePxSize += if (isBold) dFI.getBoldLength() else dFI.length
                    messagePxSize++
                }
            }
            val halvedMessageSize: Int = messagePxSize / 2
            val toCompensate: Int = 154 - halvedMessageSize
            var compensated = 0
            val sb = StringBuilder()
            while (compensated < toCompensate) {
                sb.append(" ")
                compensated += DefaultFontInfo.SPACE.length + 1
            }
            return sb.toString() + message
        }

        /**
         * Format a text with modifiers
         * Only works on paper bukkit platforms
         *
         * @param map LinkedHashMap<String, FormatterModifiers>
         * @return Component
         */
        @JvmStatic
        fun formatOneLine(map: LinkedHashMap<String, FormatterModifiers?>): Component {
            val builder: TextComponent.Builder = Component.text()
            map.forEach { (key: String, value: FormatterModifiers?) ->
                builder.append(Component.text(key.replace("&", "§")).clickEvent(value?.clickAction).hoverEvent(HoverEvent.showText(
                    Component.text(value?.hoverText?.replace("&", "§") ?: ""))))
            }
            return builder.build()
        }

        /**
         * Format a text with modifiers
         * Only works on paper bukkit platforms
         *
         * @param text String
         * @param modifier FormatterModifiers
         * @return Component
         */
        fun format(text: String, modifier: FormatterModifiers): Component =
            formatOneLine(linkedMapOf(text to modifier))
    }
}