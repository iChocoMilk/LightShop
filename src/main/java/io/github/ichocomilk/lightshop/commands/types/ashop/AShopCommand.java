package io.github.ichocomilk.lightshop.commands.types.ashop;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.permissions.Permission;

import io.github.ichocomilk.lightshop.commands.PrincipalCommand;
import io.github.ichocomilk.lightshop.commands.SubCommandStorage;

public class AShopCommand extends PrincipalCommand {

    private final Permission permission;

    public AShopCommand() {
        super(2);
        permission = new Permission("lightshop.admin");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(permission)) {
            sender.sendMessage("§4§lERROR: §cYou need the permission lightshop.admin");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(getFormat());
            return true;
        }

        final SubCommandStorage subCommand = getSubCommands().get(args[0]);        

        if (subCommand == null) {
            sender.sendMessage(getFormat());
            return true;
        }

        if (subCommand.getAnnotation().argsNeed() > args.length
            || !subCommand.execute(sender, command, label, args)) {
            sender.sendMessage(subCommand.getAnnotation().argsError());
        }
        return true;
    }

    @Override
    public String getIdentifier() {
        return "ashop";
    }

    private String getFormat() {
        return
            "\n §bShopGui §8| §7by iChocoMilk" +
            "\n " +
            "\n §b/shop §7-> | §7Open inventory"+
            "\n    §freload (messages/shops) §8- §7Reload a section" +
            "\n    §fmessages (Message) §8- §7Send a message of messages.yml";
    }
}