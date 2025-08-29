package com.Helloy.cowCannon;



import org.bukkit.Material;
import org.bukkit.entity.Cow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

public class EntityListener implements Listener {

    @EventHandler
    public void onEntityRightClick(PlayerInteractAtEntityEvent event) {
        Entity entity = event.getRightClicked();
        Player player = event.getPlayer();
    if (event.getHand() != EquipmentSlot.HAND)
        return;
    if (entity instanceof Cow && entity.hasMetadata("CowCannon") && player.getItemInHand().getType() == Material.BUCKET) {

        if (!player.hasPermission("cowcannon.cow.use")) {
            player.sendMessage("You don't have permission to milk cows ;)");

            return;
        }
            Cow cow = (Cow) event.getRightClicked();

            cow.getWorld().createExplosion(cow.getLocation(), 2.5F);
        }
    }
}
