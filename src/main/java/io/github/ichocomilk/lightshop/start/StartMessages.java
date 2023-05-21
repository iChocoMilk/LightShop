package io.github.ichocomilk.lightshop.start;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import io.github.ichocomilk.lightshop.messages.CustomMessage;
import io.github.ichocomilk.lightshop.messages.MessageAction;
import io.github.ichocomilk.lightshop.messages.ShopMessage;
import io.github.ichocomilk.lightshop.utils.MessageUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class StartMessages {

    private final CustomMessage[] messages;
    private final File file;

    private final MessageAction chat = (player, components) -> player.spigot().sendMessage(components);
    private final MessageAction actionbar = (player, components) -> player.spigot().sendMessage(ChatMessageType.ACTION_BAR, components);
    private final MessageAction none = (player, message) -> {};

    public StartMessages(File file) {
        this.file = file;
        this.messages = setMessages();
    }

    public CustomMessage[] setMessages() {
        final FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        final ShopMessage[] shopMessages = ShopMessage.values();
        final CustomMessage noneMessage = new CustomMessage(none, null);
        final CustomMessage[] newMessages = (messages == null) ? new CustomMessage[shopMessages.length] : messages;

        int position = -1;

        for (final ShopMessage shopMessage : shopMessages) {
            final String line = config.getString(shopMessage.name());

            if (line == null) {
                newMessages[++position] = noneMessage;
                continue;
            }
            newMessages[++position] = checkLine(line);
        }

        return newMessages;
    }

    private CustomMessage checkLine(String currentLine) {
        final MessageAction action = (currentLine.length() > 2) && (currentLine.charAt(1) == ':')
            ? (currentLine.charAt(0) == 'A') ? actionbar : chat
            : chat;

        return new CustomMessage(action, TextComponent.fromLegacyText(MessageUtil.translate(currentLine)));
    }

    public CustomMessage get(ShopMessage message) {
        return messages[message.ordinal()];
    }
}