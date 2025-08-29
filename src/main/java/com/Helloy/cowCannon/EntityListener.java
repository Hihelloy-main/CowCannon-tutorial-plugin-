package com.Helloy.cowCannon;



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

		System.out.println("After: " + permissions);

        if (entity.getType().equals(CowSettings.getInstance().getExplodingType()) && entity.hasMetadata("CowCannon") && player.getItemInHand().getType() == Material.BUCKET) {


            if (!player.hasPermission("cowcannon.cow.use")) {
            player.sendMessage("You don't have permission to milk cows ;)");

            return;
        }
            LivingEntity exploding_entity = (LivingEntity) event.getRightClicked();

            exploding_entity.getWorld().createExplosion(exploding_entity.getLocation(), 2.5F);
        }
    }
}
