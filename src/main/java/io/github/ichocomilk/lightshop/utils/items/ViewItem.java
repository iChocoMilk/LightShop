package io.github.ichocomilk.lightshop.utils.items;

import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;

public class ViewItem {
    private final PriceItem priceItem;
    private final Inventory viewInventory;

    public ViewItem(PriceItem priceItem, Inventory viewInventory) {
        this.priceItem = priceItem;
        this.viewInventory = viewInventory;
    }

    public PriceItem getPriceItem() {
        return priceItem;
    }

    public Inventory getViewInventory() {
        return viewInventory;
    }
}
