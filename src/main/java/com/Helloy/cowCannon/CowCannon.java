package com.Helloy.cowCannon;


import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import com.cjcrafter.foliascheduler.bukkit.BukkitTask;
import com.cjcrafter.foliascheduler.mappingio.adapter.MappingSourceNsSwitch;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_20_R3.CraftServer;
import org.bukkit.craftbukkit.v1_20_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.logging.Logger;

public final class CowCannon extends JavaPlugin {
    public static CowCannon plugin;
    public static ServerImplementation scheduler;
    public static boolean isFolia;
    public static boolean paper;
    public static boolean luminol;
    public static boolean spigot;
    public static Logger log;
    private static long ticks;
    private Object task;
    private BukkitAudiences adventure;
    private Methods methods;
    private MappingSourceNsSwitch mappingSourceNsSwitch;

    @Override
    public void onEnable() {
        ticks = 600000000;
        plugin = this;
        log = getLogger();
        methods = new Methods();

        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            isFolia = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            paper = true;
        } catch (ClassNotFoundException ignored) {}

        try {
            Class.forName("me.earthme.luminol.api.ThreadedRegion");
            luminol = true;
        } catch (ClassNotFoundException ignored) {}

        if (!isFolia && !paper && !luminol) {
            spigot = true;
        }

        if (!isSpigot()) {
            scheduler = new FoliaCompatibility(this).getServerImplementation();
        }

        if (!isLuminol() && isPaper()) {
            getLogger().info("CowCannon is running on Paper/Folia using Folia's schedulers");
        }

        if (isLuminol() && !spigot) {
            getLogger().info("CowCannon is running on Luminol using Folia's schedulers");
        }

        if (!isFolia && !paper && !luminol) {
            getLogger().info("CowCannon is running on Spigot using Bukkit's schedulers");
        }

        methods.RegisterListeners();
        methods.GetCommands();
        CowSettings.getInstance().load();


        // Don't cast directly — store as Object
        task = ThreadUtil.runGlobalTimer(ButterflyTask.getInstance(), 0, 1);

        Bukkit.getLogger().info("Ticks equals " + TimeUtil.ticksToSeconds(ticks) + " Seconds" + " Or " + TimeUtil.ticksToHours(ticks) + " Hours");
        ThreadUtil.runGlobalTimer(CowCannon::CheckForHihelloy, 60 * 20, 300 * 20);
    }

    @Override
    public void onDisable() {
        if (task != null) {
            if (task instanceof BukkitTask bukkitTask) {
                if (!bukkitTask.isCancelled()) {
                    bukkitTask.cancel();
                }
            } else if (task instanceof com.cjcrafter.foliascheduler.TaskImplementation foliaTask) {
                if (!foliaTask.isCancelled()) {
                    foliaTask.cancel();
                }
            }
        }

        if (!spigot && scheduler != null) {
            scheduler.global().cancelTasks();
        } else {
            Bukkit.getScheduler().cancelTasks(plugin);
        }
    }

    public static CowCannon getPlugin() {
        return CowCannon.plugin;
    }

    public static boolean isFolia() {
        return isFolia;
    }

    public static boolean isPaper() {
        return paper;
    }

    public static boolean isLuminol() {
        return luminol;
    }

    public static boolean isSpigot() {
        return spigot;
    }

    public static long getTicks() {
        return ticks;
    }

    public static BukkitAudiences getAdventure() {
        if (CowCannon.getPlugin().adventure == null) {
            CowCannon.getPlugin().adventure = BukkitAudiences.create(CowCannon.getPlugin());
        }
        return CowCannon.getPlugin().adventure;
    }

    public static void CheckForHihelloy() {
        if (PsychoCommand.isPlayerOnline("Hihelloy")) {
                Bukkit.getLogger().info("Hihelloy is online!");
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (player.getName().equalsIgnoreCase("Hihelloy")) {
                    getAdventure().player(player).sendMessage(MiniMessage.miniMessage().deserialize("<red><bold>Hey! You found the secret message!"));
                }
            }
        }
    }

}
