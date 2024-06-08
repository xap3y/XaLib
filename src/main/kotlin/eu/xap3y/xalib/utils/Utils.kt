package eu.xap3y.xalib.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.CompletableFuture

object Utils {

    @JvmStatic
    fun strikeLightning(loc: Location, plugin: JavaPlugin? = null) {
        if (plugin == null) loc.world?.strikeLightning(loc)
        else Bukkit.getScheduler().runTask(plugin, Runnable {
            loc.world?.strikeLightning(loc)
        })
    }

    @JvmStatic
    fun strikeLightnings(loc: Location, plugin: JavaPlugin, times: Int = 1, delay: Long = 0L): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            repeat(times) {
                strikeLightning(loc, plugin)
                Thread.sleep(delay)
            }
            true
        }
    }
}