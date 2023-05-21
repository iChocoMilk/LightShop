package io.github.ichocomilk.lightshop.gui.inventories;

import java.util.Map;

import org.bukkit.event.inventory.InventoryClickEvent;

import io.github.ichocomilk.lightshop.gui.items.GuiItem;

public class OtherGui implements GuiType {

    private final Map<Integer, GuiItem> items;

    public OtherGui(Map<Integer, GuiItem> items) {
        this.items = items;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final GuiItem guiItem = items.get(event.getSlot());
        if (guiItem != null) {
            guiItem.execute(event.getWhoClicked());
        }
    }

    public Map<Integer, GuiItem> getItems() {
        return items;
    }
}