package io.github.ichocomilk.lightshop.gui.inventories;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.gui.items.GuiItem;
import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;
import io.github.ichocomilk.lightshop.utils.items.ViewItem;

public class PageGui implements GuiType {

    private static SellOrBuyGui sellOrBuyGui;

    private final Map<Integer, GuiItem> items;
    private final Inventory inventory;

    private PageGui nextPage;
    private PageGui previousPage;

    public PageGui(int maxSize, Inventory inventory) {
        this.items = new HashMap<>(maxSize);
        this.inventory = inventory;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final GuiItem guiItem = items.get(event.getSlot());
        if (guiItem == null) {
            return;
        }

        final HumanEntity whoClicked = event.getWhoClicked();

        if (!(guiItem instanceof PriceItem)) {
            guiItem.execute(whoClicked);
            return;
        }
 
        sellOrBuyGui.add(whoClicked.getUniqueId(), new ViewItem(((PriceItem)guiItem), inventory));
        whoClicked.openInventory(sellOrBuyGui.getInventory());
    }

    public PageGui getNextPage() {
        return nextPage;
    }

    public PageGui getPreviousPage() {
        return previousPage;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void addNextPage(PageGui newPage) {
        nextPage = newPage;
    }

    public void addPreviousPage(PageGui newPage) {
        previousPage = newPage;
    }

    public void add(Integer slot, GuiItem item) {
        items.put(slot, item);
    }

    public static void setSellOrBuyGui(SellOrBuyGui gui) {
        sellOrBuyGui = gui;
    }
}