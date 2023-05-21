package io.github.ichocomilk.lightshop.pages;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import io.github.ichocomilk.lightshop.gui.inventories.PageGui;

public class PageSystem {

    private final int endSlot;
    private final int nextPageSlot;
    private final int oldPageSlot;

    private final ItemStack nextPageItem;
    private final ItemStack oldPageItem;

    public PageSystem(
        int endSlot,
        int nextPageSlot,
        int oldPageSlot,
        ItemStack nextPageItem,
        ItemStack oldPageItem
    ) {
        this.endSlot = endSlot;
        this.nextPageSlot = nextPageSlot;
        this.oldPageSlot = oldPageSlot;
        this.nextPageItem = nextPageItem;
        this.oldPageItem = oldPageItem;
    }

    public PageGui[] getPages(int amountItems, String shopTitle, int invSize, Inventory principal) {
        final int amountPages = (endSlot < amountItems)
            ? 1 + (amountItems / endSlot)
            : 1;

        System.out.println("PAGES: " + amountPages);
        return createPages(amountPages, shopTitle, invSize, principal);
    }

    private PageGui[] createPages(int amountPages, String shopTitle, int invSize, Inventory principal) {
        final Inventory firstPageInv = Bukkit.createInventory(null, invSize, shopTitle); 
        final PageGui[] pages = new PageGui[amountPages];
        final PageGui firstPage = (pages[0] = new PageGui(invSize, firstPageInv));

        firstPage.add(oldPageSlot, (humanEntity) -> humanEntity.openInventory(principal));
        firstPageInv.setItem(oldPageSlot, oldPageItem);

        for (int i = 1; i < amountPages; i++) {
            final Inventory inventory = Bukkit.createInventory(null, invSize, shopTitle);
            final PageGui currentPage = new PageGui(invSize, inventory);
            final PageGui oldPage = pages[i - 1];

            currentPage.addPreviousPage(oldPage);
            oldPage.addNextPage(currentPage);

            oldPage.getInventory().setItem(nextPageSlot, nextPageItem);
            inventory.setItem(oldPageSlot, oldPageItem);

            currentPage.add(oldPageSlot, (humanEntity) -> humanEntity.openInventory(oldPage.getInventory()));
            oldPage.add(nextPageSlot, (humanEntity) -> humanEntity.openInventory(currentPage.getInventory()));
        }

        return pages;
    }
}