package io.github.ichocomilk.lightshop;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.ichocomilk.lightshop.api.ShopApi;
import io.github.ichocomilk.lightshop.api.impl.ShopApiImpl;
import io.github.ichocomilk.lightshop.commands.CommandManager;
import io.github.ichocomilk.lightshop.economy.EconomyHook;
import io.github.ichocomilk.lightshop.listeners.InventoryListener;
import io.github.ichocomilk.lightshop.start.StartEconomy;
import io.github.ichocomilk.lightshop.start.StartManager;

public class LightShopPlugin extends JavaPlugin {

    private static ShopApi api; 

    @Override
    public void onEnable() {
        final StartEconomy startEconomy = new StartEconomy(this);
        final EconomyHook economyHook = startEconomy.getEconomy(false);

        if (economyHook == null) {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        saveDefaultConfig();

        final String folder = getDataFolder() + "/";
        final String[] defaultFiles = new String[] {
            "messages.yml", "shops/food.csv", "shops/blocks.csv"
        };

        for (String fileString : defaultFiles) {
            final File file = new File(folder + fileString);
            if (!file.exists()) {
                saveResource(fileString, false);
            }
        }

        final StartManager startManager = new StartManager(this, startEconomy, folder);
        final CommandManager commandManager = new CommandManager(startManager);

        getServer().getPluginManager().registerEvents(new InventoryListener(startManager.getStartShops().getGuis()), this);

        if (getConfig().getBoolean("enable-api")) {
            api = new ShopApiImpl(startManager, commandManager);
        }
    }

    public static ShopApi getAPI() {
        return api;
    }
}