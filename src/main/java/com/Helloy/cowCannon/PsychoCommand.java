package com.Helloy.cowCannon;

import com.Helloy.cowCannon.nms.PsychoMob1_20;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class PsychoCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command");
            return true;
        }

        final Player player = (Player) sender;
      new PsychoMob1_20(player.getLocation());

        // Use command.getName() if you need the command string
        // String commandName = command.getName();

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return List.of();
    }

    public static boolean isPlayerOnline(String playerName){
        return Bukkit.getOnlinePlayers().stream()
                .anyMatch(
                        player
                                ->
                                player
                        .getName()
                        .equalsIgnoreCase(playerName));
    }
}
