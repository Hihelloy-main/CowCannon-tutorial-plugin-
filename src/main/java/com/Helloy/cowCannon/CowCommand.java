package com.Helloy.cowCannon;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.*;
import org.bukkit.metadata.FixedMetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.bukkit.ChatColor.*;

public class CowCommand implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {


        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Only Players can use this command");
            return true;
        }

        if (args.length > 1) {

            if (args[0].equalsIgnoreCase("set")) {
                EntityType type;

                try {
                    type = EntityType.valueOf(EntityType.valueOf(args[1]).toString().toUpperCase());
                } catch (IllegalArgumentException ex) {
                    commandSender.sendMessage("Invalid entity type: " + args[1]);

                    return true;
                }

                if (!type.isSpawnable() || !type.isAlive()) {
                    commandSender.sendMessage("You can only use living entities!");

                    return true;
                }

                CowSettings.getInstance().setExplodingType(type);
                commandSender.sendMessage(ChatColor.GREEN + "Set exploding type to " + type);

                return true;
            }

            return false;
        }


        Player player = (Player) commandSender;

        LivingEntity entity = (LivingEntity) player.getWorld().spawnEntity(player.getLocation(), CowSettings.getInstance().getExplodingType());

        if (args.length == 1 && args[0].equalsIgnoreCase("baby")) {

            if (entity instanceof  Ageable) {
                ((Ageable) entity).setBaby();
            } else {
                commandSender.sendMessage("This entity cannot be a baby!");

                return true;
            }
        }

        entity.setMetadata("CowCannon", new FixedMetadataValue(CowCannon.getPlugin(), true));
        entity.setCustomName(RED + "Milk Me");
        entity.setCustomNameVisible(true);

        return true;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length == 1)
            return List.of("baby", "set");

        if (args.length == 2) {
            String name = args[1].toUpperCase();

            return Arrays.stream(EntityType.values())
                    .filter(type -> type.isSpawnable() && type.isAlive() && type.name().startsWith(name))
                    .map(Enum::name)
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
