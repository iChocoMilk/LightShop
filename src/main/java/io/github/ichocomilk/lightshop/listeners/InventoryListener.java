package io.github.ichocomilk.lightshop.listeners;

import java.util.Map;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.gui.inventories.GuiType;
import io.github.ichocomilk.lightshop.gui.inventories.SellOrBuyGui;

public class InventoryListener implements Listener {

    private final Map<Inventory, GuiType> guis;

    public InventoryListener(Map<Inventory, GuiType> guis) {
        this.guis = guis;
    } 

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        final GuiType guiType = guis.get(event.getInventory());

        if (guiType != null) {
            event.setCancelled(true);
            guiType.onClick(event);
        }
    }

    @EventHandler
    public void inventoryClose(InventoryCloseEvent event) {
        final GuiType guiType = guis.get(event.getInventory());

        if (guiType instanceof SellOrBuyGui) {
            ((SellOrBuyGui)guiType).remove(event.getPlayer().getUniqueId());
        }
    }
}