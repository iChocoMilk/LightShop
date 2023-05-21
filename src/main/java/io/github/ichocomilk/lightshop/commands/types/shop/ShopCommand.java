package io.github.ichocomilk.lightshop.commands.types.shop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.commands.PrincipalCommand;

public class ShopCommand extends PrincipalCommand {

    private final Inventory inventory;

    public ShopCommand(Inventory inventory) {
        super(0);
        this.inventory = inventory;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0 && sender instanceof Player) {
            ((Player)sender).openInventory(inventory);
            return true;
        }
        sender.sendMessage("§4§lERROR: §cYou need be a player to execute this command");
        return true;
    }

    @Override
    public String getIdentifier() {
        return "shop";
    }
}