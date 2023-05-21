package io.github.ichocomilk.lightshop.start;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import day.dean.skullcreator.SkullCreator;

import io.github.ichocomilk.lightshop.economy.EconomyHook;

import io.github.ichocomilk.lightshop.gui.inventories.GuiType;
import io.github.ichocomilk.lightshop.gui.inventories.OtherGui;
import io.github.ichocomilk.lightshop.gui.inventories.PageGui;
import io.github.ichocomilk.lightshop.gui.inventories.SellOrBuyGui;

import io.github.ichocomilk.lightshop.gui.items.GuiItem;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItem;
import io.github.ichocomilk.lightshop.gui.items.types.EconomyItemStorage;
import io.github.ichocomilk.lightshop.gui.items.types.PriceItem;
import io.github.ichocomilk.lightshop.gui.items.types.economy.BuyItem;
import io.github.ichocomilk.lightshop.gui.items.types.economy.SellItem;

import io.github.ichocomilk.lightshop.messages.ShopMessage;
import io.github.ichocomilk.lightshop.pages.PageSystem;
import io.github.ichocomilk.lightshop.shopfileformat.FileFormat;
import io.github.ichocomilk.lightshop.shopfileformat.types.CSVFormat;
import io.github.ichocomilk.lightshop.utils.MessageUtil;
import io.github.ichocomilk.lightshop.utils.items.ItemBuilder;

public class StartShops {

    private final Map<Inventory, GuiType> guis;
    private final FileFormat fileFormat;
    private final Inventory principal;
    private final String shopFolder;

    private final SkullCreator skullCreator;
    private final EconomyHook economy;

    public StartShops(FileConfiguration config, String shopFolder, EconomyHook economy, StartMessages messages) {
        this.economy = economy;
        this.skullCreator = new SkullCreator();
        this.principal = createInventory(config, "principal-inventory");
        this.fileFormat = new CSVFormat(shopFolder);
        this.shopFolder = shopFolder;
        this.guis = createGuis(config, messages);
    }

    public Map<Inventory, GuiType> createGuis(FileConfiguration config, StartMessages messages) {
        final Map<Inventory, GuiType> newGuis;

        if (guis != null) {
            newGuis = guis;
            guis.clear();
            principal.clear();
        } else {
            newGuis = new HashMap<>();
        }

        final FileFormat fileFormat = new CSVFormat(shopFolder);
        final List<String> shops = config.getStringList("shops");
        final List<String> itemLore = MessageUtil.translate(config.getStringList("item-lore"));
        final Map<Integer, GuiItem> principalItems = new HashMap<>(shops.size());

        final int endSlot = config.getInt("items-end-slot");
        final int shopSize = config.getInt("shop-inventory.rows") * 9;
        final int previousSlot = config.getInt("previous-page.slot");

        final ItemStack previousPage = createItem(config, "previous-page");
        final PageSystem pageSystem = new PageSystem(
            endSlot,
            config.getInt("next-page.slot"),
            previousSlot,
            createItem(config, "next-page"),
            previousPage);

        final SellOrBuyGui sellOrBuyGui = createSellOrBuyGui(config, messages);
        sellOrBuyGui.getInventory().setItem(previousSlot, previousPage);
        sellOrBuyGui.getItems().put(
            previousSlot,
            whoClicked -> whoClicked.openInventory(sellOrBuyGui.getViewItem(whoClicked.getUniqueId()).getViewInventory()));

        newGuis.put(sellOrBuyGui.getInventory(), sellOrBuyGui);

        PageGui.setSellOrBuyGui(sellOrBuyGui);

        for (final String shop : shops) {
            final String shopTitle = MessageUtil.translate(config.getString(shop + ".title"));
            final PriceItem[] priceItems = fileFormat.getItems(shop);
            final PageGui[] pages = pageSystem.getPages(priceItems.length, shopTitle, shopSize, principal);

            int currentItems = priceItems.length;

            for (final PageGui page : pages) {
                final Inventory inventory = page.getInventory();
                int currentSlot = -1;

                while (++currentSlot <= endSlot && --currentItems >= 0) {
                    final PriceItem priceItem = priceItems[currentItems];
                    inventory.setItem(currentSlot, createItem(priceItem.getMaterial(), itemLore, priceItem));
                    page.add(currentSlot, priceItem);
                }
                newGuis.put(page.getInventory(), page);
            }

            final ItemStack item = createItem(config, shop);
            final int slot = config.getInt(shop + ".slot");

            principal.setItem(slot, item);
            principalItems.put(slot, whoClicked -> whoClicked.openInventory(pages[0].getInventory()));
        }

        newGuis.put(principal, new OtherGui(principalItems));

        return newGuis;
    }

    private SellOrBuyGui createSellOrBuyGui(FileConfiguration config, StartMessages messages) {
        final Inventory inventory = createInventory(config, "sellOrBuy-inventory");
        final Map<Integer, GuiItem> items = new HashMap<>();

        final BuyItem buyItem = new BuyItem(
            economy,
            messages.get(ShopMessage.SUCCESSFULLY_BUY),
            messages.get(ShopMessage.NOT_ENOUGH_MONEY),
            messages.get(ShopMessage.NOT_ENOUGH_SPACE),
            messages.get(ShopMessage.CANT_BUY));

        final SellItem sellItem = new SellItem(
            economy,
            messages.get(ShopMessage.SUCCESSFULLY_SELL),
            messages.get(ShopMessage.NOT_ENOUGH_ITEMS),
            messages.get(ShopMessage.CANT_SELL));

        createEconomyButtons(buyItem, inventory, "buy-buttons.", config, items);
        createEconomyButtons(sellItem, inventory, "sell-buttons.", config, items);

        return new SellOrBuyGui(items, inventory, config.getBoolean("enable-event"));
    }

    private ItemStack createItem(Material material, List<String> lore, PriceItem prices) {
        final List<String> newLore = new ArrayList<>(lore.size());
        for (String line : lore) {
            newLore.add(line
                .replace("{0}", ""+prices.getBuyPrice())
                .replace("{1}", ""+prices.getSellPrice()));
        }
        return new ItemBuilder(material).setLore(newLore).build();
    }

    private void createEconomyButtons(EconomyItem economyItem, Inventory inventory, String name, FileConfiguration config, Map<Integer, GuiItem> items) {
        final List<Integer> amounts = config.getIntegerList(name + "amounts");
        final ItemStack item = createItem(config, name);

        for (int amount : amounts) {
            final int slot = config.getInt(name + amount + ".slot");

            item.setAmount(amount);

            inventory.setItem(slot, item);
            items.put(slot, new EconomyItemStorage(economyItem, amount));
        }
    }

    private Inventory createInventory(FileConfiguration config, String name) {
        return Bukkit.createInventory(
            null,
            config.getInt(name + ".rows") * 9,
            MessageUtil.translate(config.getString(name + ".title")));
    }

    private ItemStack createItem(FileConfiguration config, String name) {
        final String materialString = config.getString(name + ".material");
        final String itemName = config.getString(name + ".name");
        final List<String> lore = config.getStringList(name + ".lore");

        return (materialString.startsWith("HEAD"))
            ? new ItemBuilder(skullCreator.itemFromBase64(materialString.substring(5)))
                .setName(itemName)
                .setLore(lore)
                .build()

            : new ItemBuilder(Material.getMaterial(config.getString(name + ".material")))
                .setName(itemName)
                .setLore(lore)
                .build();
    }

    public Inventory getPrincipalInventory() {
        return principal;
    }

    public Map<Inventory, GuiType> getGuis() {
        return guis;
    }

    public FileFormat getFileFormat() {
        return fileFormat;
    }

    public SkullCreator getSkullCreator() {
        return skullCreator;
    }
}