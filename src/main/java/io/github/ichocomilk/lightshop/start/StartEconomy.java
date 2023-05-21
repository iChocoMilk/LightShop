package io.github.ichocomilk.lightshop.start;

import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;

import io.github.ichocomilk.lightshop.LightShopPlugin;
import io.github.ichocomilk.lightshop.economy.EconomyHook;
import io.github.ichocomilk.lightshop.economy.types.VaultHook;

import net.milkbowl.vault.economy.Economy;

public class StartEconomy {

    private final LightShopPlugin plugin;
    private final EconomyHook economyHook;

    public StartEconomy(LightShopPlugin plugin) {
        this.plugin = plugin;
        this.economyHook = getEconomy(false);
    }

    public EconomyHook getEconomy(boolean force) {
        if (economyHook == null) {
            return tryHookVault();
        }
        return (force)
            ? tryHookVault()
            : economyHook;
    }

    private VaultHook tryHookVault() {
        final Server server = plugin.getServer();

        if (server.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning("The plugin depend of Vault to work");
            return null;
        }
        final RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
        final Economy economy;

        if (rsp == null || (economy = rsp.getProvider()) == null) {
            plugin.getLogger().warning("The plugin need a economy plugin to work. Example: EssentialsX");
            return null;
        }

        return new VaultHook(economy);
    }
}
