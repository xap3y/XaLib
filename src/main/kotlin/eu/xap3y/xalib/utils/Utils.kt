package eu.xap3y.xalib.utils

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.java.JavaPlugin
import java.util.concurrent.CompletableFuture

object Utils {

    /**
     * Strike lightning at given location
     * @param loc Location
     * @param plugin JavaPlugin?
     */
    @JvmStatic
    fun strikeLightning(loc: Location, plugin: JavaPlugin? = null) {
        if (plugin == null) loc.world?.strikeLightning(loc)
        else Bukkit.getScheduler().runTask(plugin, Runnable {
            loc.world?.strikeLightning(loc)
        })
    }

    /**
     * Strike lightning at given location multiple times
     * @param loc Location
     * @param plugin JavaPlugin
     * @param times Int
     * @param delay Long (in milliseconds)
     * @return CompletableFuture<Boolean>
     */
    @JvmStatic
    fun strikeLightnings(
        loc: Location,
        plugin: JavaPlugin,
        times: Int = 1,
        delay: Long = 0L
    ): CompletableFuture<Boolean> {
        return CompletableFuture.supplyAsync {
            repeat(times) {
                strikeLightning(loc, plugin)
                Thread.sleep(delay)
            }
            true
        }
    }

    /**
     * Check if player has enough space in inventory
     * @param player Player
     * @param neededSize Int - needed empty slots
     * @return Boolean
     */
    @JvmStatic
    fun hasEnoughSpace(player: Player, neededSize: Int): Boolean {
        var emptySlots = 0
        for (slot in 0 until player.inventory.size - 5) {
            if (player.inventory.getItem(slot) == null) {
                emptySlots++
            }
        }
        return emptySlots >= neededSize
    }
}