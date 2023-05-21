package io.github.ichocomilk.lightshop.messages;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;

public interface MessageAction {
    public void send(Player player, BaseComponent[] components);   
}