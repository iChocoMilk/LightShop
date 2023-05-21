package io.github.ichocomilk.lightshop.start;

import java.io.File;

import io.github.ichocomilk.lightshop.LightShopPlugin;

public class StartManager {

    private final StartMessages startMessages;
    private final StartShops startShops;
    private final StartEconomy startEconomy;

    private final LightShopPlugin plugin;

    public StartManager(LightShopPlugin plugin, StartEconomy startEconomy, String folder) {
        this.plugin = plugin;
        this.startEconomy = startEconomy;
        this.startMessages = new StartMessages(new File(folder + "/messages.yml"));
        this.startShops = new StartShops(plugin.getConfig(), folder + "/shops/", startEconomy.getEconomy(false), startMessages);
    }

    public void reloadMessages() {
        startMessages.setMessages();
    }

    public void reloadShops() {
        plugin.reloadConfig();
        startShops.createGuis(plugin.getConfig(), startMessages);
    }

    public StartMessages getStartMessages() {
        return startMessages;
    }

    public StartShops getStartShops() {
        return startShops;
    }

    public StartEconomy getStartEconomy() {
        return startEconomy;
    }

    public LightShopPlugin getPlugin() {
        return plugin;
    }
}