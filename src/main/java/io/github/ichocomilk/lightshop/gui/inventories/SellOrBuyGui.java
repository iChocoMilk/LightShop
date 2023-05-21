package io.github.ichocomilk.lightshop.gui.inventories;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.ichocomilk.lightshop.api.events.ShopEvent;
import io.github.ichocomilk.lightshop.gui.items.GuiItem;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItem;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItemStorage;
import io.github.ichocomilk.lightshop.utils.items.ViewItem;

public class SellOrBuyGui implements GuiType {

    private final Map<UUID, ViewItem> playersViewItem;
    private final Map<Integer, GuiItem> items;
    private final Inventory inventory;
    private final boolean enableEvent;

    public SellOrBuyGui(Map<Integer, GuiItem> items, Inventory inventory, boolean enableEvent) {
        this.playersViewItem = new HashMap<>();
        this.items = items;
        this.inventory = inventory;
        this.enableEvent = enableEvent;
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        final GuiItem guiItem = items.get(event.getSlot());
        if (guiItem == null) {
            return;
        }

        if (!(guiItem instanceof EconomyItemStorage)) {
            guiItem.execute(event.getWhoClicked());
            return;
        }

        final Player player = (Player)event.getWhoClicked();
        final EconomyItemStorage itemStorage = (EconomyItemStorage)guiItem;
        final int amount = itemStorage.getAmount();
        final ViewItem itemView = playersViewItem.get(player.getUniqueId());
        final EconomyItem item = itemStorage.getItem();

        if (!enableEvent) {
            item.execute(player, amount, itemView.getPriceItem(), new ItemStack(itemView.getPriceItem().getMaterial(), amount));
            return;
        }
 
        final ShopEvent shopEvent = new ShopEvent(item.getAction(), amount, itemView, player);
        Bukkit.getPluginManager().callEvent(shopEvent);

        if (!shopEvent.isCancelled()) {
            item.execute(player, amount, itemView.getPriceItem(), shopEvent.getItemToGive());
        }
    }

    public void add(UUID uuid, ViewItem viewItem) {
        playersViewItem.put(uuid, viewItem);
    }

    public void remove(UUID uuid) {
        playersViewItem.remove(uuid);
    }

    public ViewItem getViewItem(UUID uuid) {
        return playersViewItem.get(uuid);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Map<Integer, GuiItem> getItems() {
        return items;
    }
}