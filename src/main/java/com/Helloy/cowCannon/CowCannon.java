package com.Helloy.cowCannon;


import com.Helloy.cowCannon.Util.ThreadUtil;
import com.Helloy.cowCannon.Util.TimeUtil;
import com.cjcrafter.foliascheduler.FoliaCompatibility;
import com.cjcrafter.foliascheduler.ServerImplementation;
import com.cjcrafter.foliascheduler.folia.FoliaTask;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.scheduler.BukkitWorker;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.function.Consumer;
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

    @Override
    public void onEnable() {
        ticks = 600000000;
        plugin = this;
        scheduler = new FoliaCompatibility(plugin).getServerImplementation();

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

        if (!isLuminol() && isPaper()) {
            getLogger().info("CowCannon is running on Paper/Folia using Folia's schedulers");
        }

        if (isLuminol() && !spigot) {
            getLogger().info("CowCannon is running on Luminol using Folia's schedulers");
        }

        if (!isFolia && !paper && !luminol) {
            spigot = true;
            getLogger().info("CowCannon is running on Spigot using Bukkit's schedulers");
        }

     getServer().getPluginManager().registerEvents(new EntityListener(), this);
     getCommand("cow").setExecutor(new CowCommand());
     CowSettings.getInstance().load();
     Bukkit.getLogger().info("Ticks equals " + TimeUtil.ticksToSeconds(ticks) + " Seconds" + " Or " + TimeUtil.ticksToHours(ticks) + " Hours");
    }

    @Override
    public void onDisable() {
        if (!spigot) {
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
