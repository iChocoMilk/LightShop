package io.github.ichocomilk.lightshop.utils.items;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.github.ichocomilk.lightshop.utils.MessageUtil;

public class ItemBuilder {

    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material newMaterial) {
        this.item = new ItemStack((newMaterial == null) ? Material.BARRIER : newMaterial);
        this.meta = Bukkit.getItemFactory().getItemMeta(newMaterial);
    }

    public ItemBuilder(ItemStack item) {
        this.item = item;
        this.meta = item.getItemMeta();
    }

    public ItemBuilder setName(String name) {
        if (name == null) {
            return this;
        }
        meta.setDisplayName(MessageUtil.translate(name));
        return this;
    }

    public ItemBuilder setLore(List<String> lore) {
        if (lore.isEmpty()) {
            return this;
        }
        meta.setLore(MessageUtil.translate(lore));
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }
}