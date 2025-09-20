package com.Helloy.cowCannon;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.security.Permission;
import java.util.List;

public class PermCommand implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only players can use this command");
            return true;
        }

        if (commandSender.hasPermission("cowcannon.cow.use")) {
            commandSender.removeAttachment(commandSender.addAttachment(CowCannon.getPlugin(), "cowcannon.cow.use", false));
            commandSender.sendMessage("Permission cowcannon.cow.use removed");
        } else {
            commandSender.addAttachment(CowCannon.getPlugin(), "cowcannon.cow.use", true);
            commandSender.sendMessage("Permission cowcannon.cow.use added");
        }


        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        return List.of();
    }
}
