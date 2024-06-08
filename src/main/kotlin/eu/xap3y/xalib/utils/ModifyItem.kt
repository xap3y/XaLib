package eu.xap3y.xalib.utils

import eu.xap3y.xalib.managers.Texter
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

object ModifyItem {

    /**
     * Rename item
     * @param item ItemStack
     * @param name String
     * @return ItemStack
     */
    @JvmStatic
    fun renameItem(item: ItemStack, name: String): ItemStack {
        val meta = item.itemMeta
        meta?.setDisplayName(Texter.coloredText(name))
        item.itemMeta = meta
        return item
    }

    /**
     * Add lore to item
     * @param item ItemStack
     * @param lore List<String>
     * @return ItemStack
     */
    @JvmStatic
    fun addLore(item: ItemStack, lore: List<String>): ItemStack {
        val meta = item.itemMeta
        val loreList = meta?.lore ?: mutableListOf()
        loreList.addAll(lore.map { Texter.coloredText(it) })
        meta?.lore = loreList
        item.itemMeta = meta
        return item
    }

    /**
     * Set lore to item
     * @param item ItemStack
     * @param lore List<String>
     * @return ItemStack
     */
    @JvmStatic
    fun setLore(item: ItemStack, lore: List<String>): ItemStack {
        val meta = item.itemMeta
        meta?.lore = lore.map { Texter.coloredText(it) }
        item.itemMeta = meta
        return item
    }

    /**
     * Generate item based on given parameters
     * @param item ItemStack - base item
     * @param name String? - new name
     * @param lore List<String>? - new lore
     * @return ItemStack - generated item
     */
    @JvmStatic
    fun genItem(item: ItemStack, name: String? = null, lore: List<String>? = null): ItemStack {
        var newItem: ItemStack = item
        if (name != null)
            newItem = renameItem(newItem, name)

        if (lore != null)
            newItem = setLore(newItem, lore)

        return newItem
    }

    /**
     * Set player's head to any itemstack
     * @param p Player
     * @param item ItemStack
     */
    @JvmStatic
    fun applyHead(p: LivingEntity, item: ItemStack) {
        p.equipment?.helmet = item
    }
}