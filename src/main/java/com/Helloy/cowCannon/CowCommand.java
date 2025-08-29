package com.Helloy.cowCannon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.ChatColor.*;

public class CowCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only Players can use this command");
            return true;
        }

        if (args.length > 1)
            return false;


        Player player = (Player) commandSender;

        Cow cow = player.getWorld().spawn(player.getLocation(), Cow.class);

        if (args.length == 1 && args[0].equalsIgnoreCase("baby"))
            cow.setBaby();

        cow.setMetadata("CowCannon", new FixedMetadataValue(CowCannon.getPlugin(), true));
        cow.setCustomName(RED + "Milk Me");
        cow.setCustomNameVisible(true);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
            return List.of("baby");

        return new ArrayList<>();
    }
}
