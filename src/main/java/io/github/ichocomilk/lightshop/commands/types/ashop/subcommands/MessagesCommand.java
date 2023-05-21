package io.github.ichocomilk.lightshop.commands.types.ashop.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.ichocomilk.lightshop.commands.annotations.SubCommand;
import io.github.ichocomilk.lightshop.commands.types.ashop.AShopCommand;
import io.github.ichocomilk.lightshop.commands.paramstype.ShortParams;
import io.github.ichocomilk.lightshop.messages.ShopMessage;
import io.github.ichocomilk.lightshop.start.StartMessages;

@SubCommand(
    identifier = "messages",
    argsError = "§4§lERROR: §cInvalid message. /ashop messages (message), example CANT_SELL",
    principalCommand = AShopCommand.class
)
public class MessagesCommand implements ShortParams {

    private final StartMessages startMessages;

    public MessagesCommand(StartMessages startMessages) {
        this.startMessages = startMessages;
    }

    public boolean execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§4§lERROR: §cYou need be a player to execute this command");
            return true;
        }

        final ShopMessage shopMessage = ShopMessage.valueOf(args[1].toUpperCase());

        if (shopMessage == null) {
            return false;
        }

        startMessages.get(shopMessage).send((Player)sender);
        return true;
    }
}