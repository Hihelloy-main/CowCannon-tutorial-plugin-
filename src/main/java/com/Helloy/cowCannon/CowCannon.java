package com.Helloy.cowCannon;

import com.Helloy.cowCannon.Util.ThreadUtil;
import com.Helloy.cowCannon.Util.TimeUtil;
import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

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
    private Object task; // Can be BukkitTask or Folia TaskImplementation

    @Override
    public void onEnable() {
        ticks = 600000000;
        plugin = this;
        log = getLogger();

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

        getServer().getPluginManager().registerEvents(new EntityListener(), this);
        getCommand("cow").setExecutor(new CowCommand());
        getCommand("butterfly").setExecutor(new ButterflyCommand());
        CowSettings.getInstance().load();

        // Don't cast directly — store as Object
        task = ThreadUtil.runGlobalTimer(ButterflyTask.getInstance(), 0, 1);

        Bukkit.getLogger().info("Ticks equals " + TimeUtil.ticksToSeconds(ticks) + " Seconds" + " Or " + TimeUtil.ticksToHours(ticks) + " Hours");
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
}
