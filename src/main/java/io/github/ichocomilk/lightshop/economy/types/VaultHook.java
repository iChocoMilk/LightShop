package io.github.ichocomilk.lightshop.economy.types;

import org.bukkit.entity.Player;

import io.github.ichocomilk.lightshop.economy.EconomyHook;
import net.milkbowl.vault.economy.Economy;

public class VaultHook implements EconomyHook {

    private final Economy economy;

    public VaultHook(Economy economy) {
        this.economy = economy;
    }

    @Override
    public double getMoney(Player player) {
        return economy.getBalance(player);
    }

    @Override
    public boolean deposit(Player player, double balance) {
        return economy.depositPlayer(player, balance).transactionSuccess();
    }

    @Override
    public boolean withdraw(Player player, double balance) {
        return economy.withdrawPlayer(player, balance).transactionSuccess();
    }
}