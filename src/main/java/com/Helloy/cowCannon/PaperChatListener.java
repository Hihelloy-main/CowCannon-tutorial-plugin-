package com.Helloy.cowCannon;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PaperChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncChatEvent event) {
        String message = event.message().toString();

        for (Audience audience : event.viewers()) {
            if (audience instanceof Player) {
                audience.sendActionBar(Component.text("You sent a message!").color(TextColor.fromHexString("#FFA500")).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Message: " + message).color(NamedTextColor.YELLOW).decorate(TextDecoration.ITALIC))));
                long playertime = ((Player) audience).getPlayerTime();
                audience.sendMessage(Component.text("Your current time is: " + playertime).color(TextColor.fromHexString("#ADD8E6")).decorate(TextDecoration.ITALIC).hoverEvent(HoverEvent.showText(Component.text("Time is relative!").color(NamedTextColor.AQUA).decorate(TextDecoration.BOLD))));
                ((Player) audience).setDisplayName("[Chatter] " + ((Player) audience).getName());
            }
        }

        if (message.contains("cow")) {
            event.getPlayer().sendMessage(Component.text("Did someone say cow? Moo! 🐄").color(TextColor.fromHexString("#00FF00")).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Cows are awesome!").color(NamedTextColor.YELLOW).decorate(TextDecoration.ITALIC).clickEvent(ClickEvent.runCommand("/butterfly")))));
        }
        if (message.contains("ee")) {
            event.getPlayer().sendMessage(Component.text("EE sucks shit").color(TextColor.fromHexString("#FF0000")).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Horrible ass clan").color(NamedTextColor.RED).decorate(TextDecoration.BOLD))));
        }

        TextComponent textComponent = (TextComponent) event.message();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        Component replacedText = miniMessage.deserialize(textComponent.content());
        event.message(replacedText);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().sendMessage(Component
                .text("Welcome to the server! Enjoy your stay!")
                .color(TextColor.fromHexString("#FFFF00"))
                .decorate(TextDecoration.ITALIC)
                .hoverEvent(HoverEvent.showText(Component.text("EE IS ASS").color(NamedTextColor.RED).decorate(TextDecoration.BOLD)))
        );
    }
}
