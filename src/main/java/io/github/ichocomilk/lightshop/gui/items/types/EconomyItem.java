package io.github.ichocomilk.lightshop.gui.items.types;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.github.ichocomilk.lightshop.api.events.ShopEvent.ShopAction;
import io.github.ichocomilk.lightshop.gui.items.GuiItem;

public interface EconomyItem extends GuiItem {

    public void execute(Player player, int amount, PriceItem item, ItemStack itemToGive);
    public ShopAction getAction();

    @Override
    default void execute(HumanEntity whoClicked) {
        whoClicked.sendMessage("EconomyItem can't be execute with this method");
    }
}