package io.github.ichocomilk.lightshop.economy;

import org.bukkit.entity.Player;

public interface EconomyHook {

    public double getMoney(Player player);
    public boolean deposit(Player player, double balance);
    public boolean withdraw(Player player, double balance);
}