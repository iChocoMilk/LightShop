package io.github.ichocomilk.lightshop.api;

import java.util.Collection;
import java.util.Map;

import org.bukkit.inventory.Inventory;

import io.github.ichocomilk.lightshop.commands.CommandManager;
import io.github.ichocomilk.lightshop.commands.PrincipalCommand;
import io.github.ichocomilk.lightshop.gui.inventories.GuiType;
import io.github.ichocomilk.lightshop.shopfileformat.FileFormat;
import io.github.ichocomilk.lightshop.start.StartManager;

public interface ShopApi {

    /**
     * Get all inventories in the plugin
     * this include pages of shops, sellorbuygui, principalgui, etc
     * 
     * @return All guis
     */
    public Map<Inventory, GuiType> getGuis();

    /**
     * Get all principal commands
     * this include ashop, shop, and others
     * 
     * @return All commands
     */
    public Collection<PrincipalCommand> getPrincipalCommands();

    /**
     * Get the file format
     * 
     * @return CSVFormat
     */
    public FileFormat getFileFormat();

    /**
     * With this you can add subcommands to principal commands
     * or add others commands
     * 
     * @return CommandManager
     */
    public CommandManager getCommandManager();

    /**
     * With this you can reload the shop or messages,
     * access to guis, messages, economyhook, etc
     * 
     * @return StartManager
     */
    public StartManager getStartManager();
}