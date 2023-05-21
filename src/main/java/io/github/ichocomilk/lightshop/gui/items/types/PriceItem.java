package io.github.ichocomilk.lightshop.gui.items.types;

import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;

import io.github.ichocomilk.lightshop.gui.items.GuiItem;

public class PriceItem implements GuiItem {

    private final int[] prices;
    private final Material material;

    public PriceItem(int buyPrice, int sellPrice, Material material) {
        this.prices = new int[] { buyPrice, sellPrice };
        this.material = material;
    }

    public int getBuyPrice() {
        return prices[0];
    }

    public int getSellPrice() {
        return prices[1];
    }

    public Material getMaterial() {
        return material;
    }

    @Override
    public void execute(HumanEntity whoClicked) {
        whoClicked.sendMessage("PriceItem can't be execute");
    }
}