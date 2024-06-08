package eu.xap3y.managers

import eu.xap3y.objects.TextModifier
import eu.xap3y.objects.TexterObj
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
    fun response(p0: CommandSender, text: String, modifiers: TextModifier = TextModifier()) {
        val textToSend: String = if (modifiers.colored) coloredText(text) else text
        val prefix: String = if (modifiers.withPrefix) coloredText(data.prefix) else ""
        p0.sendMessage("$prefix$textToSend")
    }

    /**
     * Log a text into the console
     *
     * @param text The text that will be logged
     * @param modifiers The text modifiers
     */
    fun console(text: String, modifiers: TextModifier = TextModifier()) =
        response(Bukkit.getConsoleSender(), text, modifiers)

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
        @JvmStatic
        fun coloredText(text: String): String =
            ChatColor.translateAlternateColorCodes('&', text)

    }
}