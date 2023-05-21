package io.github.ichocomilk.lightshop.gui.items.types.economy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.ichocomilk.lightshop.api.events.ShopEvent.ShopAction;
import io.github.ichocomilk.lightshop.economy.EconomyHook;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItem;
import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;
import io.github.ichocomilk.lightshop.messages.CustomMessage;

public class SellItem implements EconomyItem {

    private final CustomMessage successfullySell;
    private final CustomMessage noEnoughItems;
    private final CustomMessage cantSell;

    private final EconomyHook economy;

    public SellItem(
        EconomyHook economy,
        CustomMessage successfullySell,
        CustomMessage noEnoughItems,
        CustomMessage cantSell
    ) {
        this.economy = economy;
        this.successfullySell = successfullySell;
        this.noEnoughItems = noEnoughItems;
        this.cantSell = cantSell;
    }

    @Override
    public void execute(Player player, int amount, PriceItem item, ItemStack itemToGive) {
        final PlayerInventory inventory = player.getInventory();

        if (item.getSellPrice() == 0) {
            cantSell.send(player);
            return;
        }

        final int slot = inventory.first(itemToGive);
        if (slot == -1) {
            noEnoughItems.send(player);
            return;
        }

        if (economy.deposit(player, amount * item.getSellPrice())) {
            inventory.setItem(slot, null);
            successfullySell.send(player);
        }
    }

    @Override
    public ShopAction getAction() {
        return ShopAction.SELL_ITEM;
    }
}