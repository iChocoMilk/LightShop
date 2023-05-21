package io.github.ichocomilk.lightshop.gui.items.types.economy;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import io.github.ichocomilk.lightshop.api.events.ShopEvent.ShopAction;
import io.github.ichocomilk.lightshop.economy.EconomyHook;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItem;
import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;
import io.github.ichocomilk.lightshop.messages.CustomMessage;

public class BuyItem implements EconomyItem {

    private final CustomMessage successfullyBuy;
    private final CustomMessage noEnoughMoney;
    private final CustomMessage noEnoughSpace;
    private final CustomMessage cantBuy;

    private final EconomyHook economy;

    public BuyItem(
        EconomyHook economy,
        CustomMessage successfullyBuy,
        CustomMessage noEnoughMoney,
        CustomMessage noEnoughSpace,
        CustomMessage cantBuy
    ) {
        this.economy = economy;
        this.successfullyBuy = successfullyBuy;
        this.noEnoughMoney = noEnoughMoney;
        this.noEnoughSpace = noEnoughSpace;
        this.cantBuy = cantBuy;
    }

    @Override
    public void execute(Player player, int amount, PriceItem item, ItemStack itemToGive) {

        if (item.getBuyPrice() == 0) {
            cantBuy.send(player);
            return;
        }

        final PlayerInventory inventory = player.getInventory();
        final int emptySlot = inventory.firstEmpty();

        if (emptySlot == -1) {
            noEnoughSpace.send(player);
            return;
        }

        final int price = amount * item.getSellPrice();

        if (economy.getMoney(player) < price || !economy.withdraw(player, price)) {
            noEnoughMoney.send(player);
            return;
        }

        inventory.setItem(emptySlot, itemToGive);
        successfullyBuy.send(player);
    }


    @Override
    public ShopAction getAction() {
        return ShopAction.BUY_ITEM;
    }
}