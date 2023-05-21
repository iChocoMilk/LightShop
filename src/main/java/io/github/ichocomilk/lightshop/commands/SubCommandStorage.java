package io.github.ichocomilk.lightshop.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import io.github.ichocomilk.lightshop.commands.annotations.SubCommand;
import io.github.ichocomilk.lightshop.commands.paramstype.DefaultParams;
import io.github.ichocomilk.lightshop.commands.paramstype.ShortParams;

public class SubCommandStorage {

    private final Object subCommand;
    private final SubCommand annotation;

    public SubCommandStorage(Object subCommand) {
        this.subCommand = subCommand;
        this.annotation = subCommand.getClass().getDeclaredAnnotation(SubCommand.class);
    }

    public boolean execute(CommandSender sender, Command command, String label, String[] args) {
        return (subCommand instanceof ShortParams)
            ? ((ShortParams)subCommand).execute(sender, args)
            : ((DefaultParams)subCommand).execute(sender, command, label, args);
    }

    public SubCommand getAnnotation() {
        return annotation;
    }
}