package eu.xap3y.xalib.managers

import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ConfigManager(private val plugin: JavaPlugin) {

    /**
     * Reloads server config file
     */
    fun reloadConfig() {
        if (!plugin.dataFolder.exists()) {
            plugin.dataFolder.mkdir()
        }

        val configFile = File(plugin.dataFolder, "config.yml")

        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false)
        } else {
            plugin.config.load(configFile)
        }
    }
}