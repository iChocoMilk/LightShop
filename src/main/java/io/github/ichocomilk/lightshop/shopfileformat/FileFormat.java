package io.github.ichocomilk.lightshop.shopfileformat;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;

public interface FileFormat {
    final PriceItem invalidItem = new PriceItem(0, 0, Material.STONE); 

    public PriceItem[] getItems(String shop);

    default PriceItem createItem(int buyPrice, int sellPrice, String materialString, String shop) {
        final Material material = Material.getMaterial(materialString);
        if (material == null) {
            Bukkit.getLogger().warning("SHOP:" + shop + " the material " + materialString + " don't exist");
            return invalidItem;
        }
        return new PriceItem(buyPrice, sellPrice, material);
    }
}