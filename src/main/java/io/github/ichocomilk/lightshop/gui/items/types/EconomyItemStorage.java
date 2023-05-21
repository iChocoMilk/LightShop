package io.github.ichocomilk.lightshop.gui.items.types;

import org.bukkit.entity.HumanEntity;

import io.github.ichocomilk.lightshop.gui.items.GuiItem;

public class EconomyItemStorage implements GuiItem {

    private final EconomyItem item;
    private final int amount;

    public EconomyItemStorage(EconomyItem item, int amount) {
        this.item = item;
        this.amount = amount;
    }

    public EconomyItem getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void execute(HumanEntity whoClicked) {
        whoClicked.sendMessage("EconomyItemStorage can't be execute");
    }
}
