package com.Helloy.cowCannon;


import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class SpigotChatListener implements Listener {


    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();

        CowCannon.getAdventure().player(player).sendActionBar(
                Component.text("You sent a message!")
                        .color(TextColor.fromHexString("#FFA500"))
                        .decorate(TextDecoration.BOLD)
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Message: " + message)
                                        .color(NamedTextColor.YELLOW)
                                        .decorate(TextDecoration.ITALIC)
                        ))
        );
        long playertime = player.getPlayerTime();
        CowCannon.getAdventure().player(player).sendMessage(
                Component.text("Your current time is: " + playertime)
                        .color(TextColor.fromHexString("#ADD8E6"))
                        .decorate(TextDecoration.ITALIC)
                        .hoverEvent(HoverEvent.showText(
                                Component.text("Time is relative!")
                                        .color(NamedTextColor.AQUA)
                                        .decorate(TextDecoration.BOLD)
                        ))
        );
        player.setDisplayName("[Chatter] " + player.getName());

        if (message.contains("cow")) {
            CowCannon.getAdventure().player(player).sendMessage(
                    Component.text("Did someone say cow? Moo! 🐄")
                            .color(TextColor.fromHexString("#00FF00"))
                            .decorate(TextDecoration.BOLD)
                            .hoverEvent(HoverEvent.showText(
                                    Component.text("Cows are awesome!")
                                            .color(NamedTextColor.YELLOW)
                                            .decorate(TextDecoration.ITALIC)
                                            .clickEvent(ClickEvent.runCommand("/butterfly"))
                            ))
            );
        }
        if (message.contains("ee")) {
            CowCannon.getAdventure().player(player).sendMessage(
                    Component.text("EE sucks shit")
                            .color(TextColor.fromHexString("#FF0000"))
                            .decorate(TextDecoration.BOLD)
                            .hoverEvent(HoverEvent.showText(
                                    Component.text("Horrible ass clan")
                                            .color(NamedTextColor.RED)
                                            .decorate(TextDecoration.BOLD)
                            ))
            );
        }

        MiniMessage miniMessage = MiniMessage.miniMessage();
        Component replacedText = miniMessage.deserialize(message);
        String legacy = LegacyComponentSerializer.legacySection().serialize(replacedText);
        event.setMessage(legacy);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        CowCannon.getAdventure().player(event.getPlayer()).sendMessage(
                Component.text("Welcome to the server! Enjoy your stay!")
                        .color(TextColor.fromHexString("#FFFF00"))
                        .decorate(TextDecoration.ITALIC)
                        .hoverEvent(HoverEvent.showText(
                                Component.text("EE IS ASS")
                                        .color(NamedTextColor.RED)
                                        .decorate(TextDecoration.BOLD)
                        ))
        );
    }
}