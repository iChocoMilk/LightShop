package io.github.ichocomilk.lightshop.commands;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.command.CommandExecutor;

public abstract class PrincipalCommand implements CommandExecutor {

    private final Map<String, SubCommandStorage> subCommands;

    public PrincipalCommand(int defaultSize) {
        subCommands = new HashMap<>(defaultSize);
    }

    public Map<String, SubCommandStorage> getSubCommands() {
        return subCommands;
    }

    public abstract String getIdentifier();
}