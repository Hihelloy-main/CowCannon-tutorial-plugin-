package com.Helloy.cowCannon;




import com.destroystokyo.paper.entity.Pathfinder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityListener implements Listener {

    private Map<UUID, PermissionAttachment> permissions = new HashMap<>();

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
    if (event.getHand() != EquipmentSlot.HAND)
        return;

        // iterate through player.getEffectivePermissions() as foreach
		for (PermissionAttachmentInfo permission : player.getEffectivePermissions()) {
			PermissionAttachment attachment = permission.getAttachment();

			System.out.println("Permission: " + permission.getPermission() + " from " + (attachment == null ? "default" : attachment.getPlugin().getName()));
		}

		System.out.println("Before: " + permissions);

		if (permissions.containsKey(player.getUniqueId())) {
			PermissionAttachment permission = permissions.remove(player.getUniqueId());
			player.removeAttachment(permission);

			player.sendMessage("You no longer have the perm!");

		} else {
			PermissionAttachment permission = player.addAttachment(CowCannon.getPlugin(), "funky.demo.test", true);

			permissions.put(player.getUniqueId(), permission);
			player.sendMessage("You now have the perm!");
		}

        if (CowCannon.isSpigot()) {
          CowCannon.getAdventure().player(player).sendMessage(Component.text("You clicked on a " + entity.getType().name()).color(TextColor.color(0x00FF00)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Right click with a bucket to make it explode!").color(TextColor.color(0xFFFF00)))).decorate(TextDecoration.BOLD));
           CowCannon.getAdventure().player(player).sendMessage(Component.text("After: " + permissions).color(TextColor.color(0x00FF00)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("You're now viewing the permissions map!").color(TextColor.color(0xFFFF00)))).decorate(TextDecoration.BOLD).clickEvent(ClickEvent.openUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")));
        }
        if (CowCannon.isPaper()) {
            player.sendMessage(Component.text("You clicked on a " + entity.getType().name()).color(TextColor.color(0x00FF00)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Right click with a bucket to make it explode!").color(TextColor.color(0xFFFF00)))).decorate(TextDecoration.BOLD));
            player.sendMessage(Component.text("After: " + permissions).color(TextColor.color(0x00FF00)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("You're now viewing the permissions map!").color(TextColor.color(0xFFFF00)))).decorate(TextDecoration.BOLD).clickEvent(ClickEvent.openUrl("https://www.youtube.com/watch?v=dQw4w9WgXcQ&list=RDdQw4w9WgXcQ&start_radio=1")));
        }



        if (entity.getType().equals(CowSettings.getInstance().getExplodingType()) && entity.hasMetadata("CowCannon") && player.getItemInHand().getType() == Material.BUCKET) {


            if (!player.hasPermission("cowcannon.cow.use")) {
                if (CowCannon.isPaper()) {
                    player.sendMessage(Component.text("You do not have permission to make this entity explode!").color(TextColor.color(0xFF0000)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Missing Permission: cowcannon.cow.use").color(TextColor.color(0xFFFF00)))));
                }
                if (CowCannon.isSpigot()) {
                   CowCannon.getAdventure().player(player).sendMessage(Component.text("You do not have permission to make this entity explode!").color(TextColor.color(0xFF0000)).decorate(TextDecoration.BOLD).hoverEvent(HoverEvent.showText(Component.text("Missing Permission: cowcannon.cow.use").color(TextColor.color(0xFFFF00)))));
                }

            return;
        }
            LivingEntity exploding_entity = (LivingEntity) event.getRightClicked();

            exploding_entity.getWorld().createExplosion(exploding_entity.getLocation(), 2.5F);
        }

    }
}
