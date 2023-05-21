package io.github.ichocomilk.lightshop.commands;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

import io.github.ichocomilk.lightshop.commands.types.ashop.AShopCommand;
import io.github.ichocomilk.lightshop.commands.types.ashop.subcommands.MessagesCommand;
import io.github.ichocomilk.lightshop.commands.types.ashop.subcommands.ReloadCommand;
import io.github.ichocomilk.lightshop.commands.types.shop.ShopCommand;
import io.github.ichocomilk.lightshop.start.StartManager;

public class CommandManager {

    private final Map<Class<? extends PrincipalCommand>, PrincipalCommand> commands;

    public CommandManager(StartManager startManager) {
        commands = new HashMap<>(2);
        resetCommands(startManager);
    }

    public void resetCommands(StartManager startManager) {
        commands.clear();

        add(new AShopCommand());
        add(new ShopCommand(startManager.getStartShops().getPrincipalInventory()));

        // AShop subcommands
        addSub(new ReloadCommand(startManager));
        addSub(new MessagesCommand(startManager.getStartMessages()));
    }

    public Collection<PrincipalCommand> getCommands() {
        return commands.values();
    }

    public void addSub(Object subCommand) {
        final SubCommandStorage params = new SubCommandStorage(subCommand);
        commands.get(params.getAnnotation().principalCommand()).getSubCommands().put(params.getAnnotation().identifier(), params);
    }

    public void add(PrincipalCommand command) {
        Bukkit.getPluginCommand(command.getIdentifier()).setExecutor(command);
        commands.put(command.getClass(), command);
    }
}