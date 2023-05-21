package io.github.ichocomilk.lightshop.messages;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.chat.BaseComponent;

public class CustomMessage {

    private MessageAction action;
    private BaseComponent[] components;

    public CustomMessage(MessageAction action, BaseComponent[] components) {
        setValues(action, components);
    }

    public void setValues(MessageAction action, BaseComponent[] components) {
        this.action = action;
        this.components = components;
    }

    public void send(Player player) {
        action.send(player, components);
    }
}