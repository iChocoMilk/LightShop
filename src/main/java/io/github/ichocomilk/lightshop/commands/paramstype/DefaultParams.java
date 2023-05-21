package io.github.ichocomilk.lightshop.commands.paramstype;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public interface DefaultParams {
    public boolean execute(CommandSender sender, Command command, String label, String[] args);
}
