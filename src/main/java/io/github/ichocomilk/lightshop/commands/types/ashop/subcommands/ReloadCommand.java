package io.github.ichocomilk.lightshop.commands.types.ashop.subcommands;

import org.bukkit.command.CommandSender;

import io.github.ichocomilk.lightshop.commands.annotations.SubCommand;
import io.github.ichocomilk.lightshop.commands.types.ashop.AShopCommand;
import io.github.ichocomilk.lightshop.commands.paramstype.ShortParams;
import io.github.ichocomilk.lightshop.start.StartManager;

@SubCommand(
    identifier = "reload",
    argsError = "§4§lERROR: §cAvaible options: messages, shops",
    principalCommand = AShopCommand.class
)
public class ReloadCommand implements ShortParams {

    private final StartManager manager;

    public ReloadCommand(StartManager manager) {
        this.manager = manager;
    }

    public boolean execute(CommandSender sender, String[] args) {
        switch (args[0].toLowerCase()) {
            case "messages":
                manager.reloadMessages();
                sender.sendMessage("§aMessages reloaded!");
                return true;
            
            case "shops":
                manager.reloadShops();
                sender.sendMessage("§aShops reloaded!");
                return true;
        }
        return false;
    }
}