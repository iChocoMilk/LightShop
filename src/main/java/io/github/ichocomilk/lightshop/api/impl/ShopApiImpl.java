package io.github.ichocomilk.lightshop.api.impl;

import java.util.Collection;
import java.util.Map;

import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.api.ShopApi;
import io.github.ichocomilk.lightshop.commands.CommandManager;
import io.github.ichocomilk.lightshop.commands.PrincipalCommand;
import io.github.ichocomilk.lightshop.gui.inventories.GuiType;
import io.github.ichocomilk.lightshop.shopfileformat.FileFormat;
import io.github.ichocomilk.lightshop.start.StartManager;

public class ShopApiImpl implements ShopApi {

    private final StartManager startManager;
    private final CommandManager commandManager;

    public ShopApiImpl(StartManager startManager, CommandManager commandManager) {
        this.startManager = startManager;
        this.commandManager = commandManager;
    }

    public Map<Inventory, GuiType> getGuis() {
        return startManager.getStartShops().getGuis();
    }

    public Collection<PrincipalCommand> getPrincipalCommands() {
        return commandManager.getCommands();
    }

    public FileFormat getFileFormat() {
        return startManager.getStartShops().getFileFormat();
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public StartManager getStartManager() {
        return startManager;
    }
}