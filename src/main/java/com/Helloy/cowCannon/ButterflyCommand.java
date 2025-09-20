package com.Helloy.cowCannon;

import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;


public final class ButterflyCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        Player player = (Player) sender;
        ButterflyTask instance = ButterflyTask.getInstance();

        if (instance.hasPlayer(player.getUniqueId())) {
            instance.removePlayer(player.getUniqueId());
            player.sendMessage(ChatColor.RED + "You are no longer viewing butterfly wings!");

        } else {
            instance.addPlayer(player.getUniqueId());
            player.sendMessage(ChatColor.GREEN + "You are now viewing butterfly wings!");
        }
        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }
}