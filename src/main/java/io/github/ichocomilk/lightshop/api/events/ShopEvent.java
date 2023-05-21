package io.github.ichocomilk.lightshop.api.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

import io.github.ichocomilk.lightshop.utils.items.ViewItem;

public class ShopEvent extends Event implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();

    private final ShopAction type;
    private final int amount;
    private final ViewItem item;
    private final Player player;

    private ItemStack itemToGive;
    private boolean cancelled;

    public static enum ShopAction {
        SELL_ITEM,
        BUY_ITEM
    }

    public ShopEvent(ShopAction type, int amount, ViewItem item, Player player) {
        this.type = type;
        this.amount = amount;
        this.item = item;
        this.player = player;
        this.cancelled = false;
    }

    public ShopAction getAction() {
        return type;
    }

    public ViewItem getItem() {
        return item;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getItemToGive() {
        return (itemToGive == null)
            ? new ItemStack(item.getPriceItem().getMaterial(), amount)
            : itemToGive;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    public void setItemToGive(ItemStack item) {
        itemToGive = item;
    }
}