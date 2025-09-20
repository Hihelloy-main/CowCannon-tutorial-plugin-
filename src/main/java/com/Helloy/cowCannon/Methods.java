package com.Helloy.cowCannon;

import com.destroystokyo.paper.utils.PaperPluginLogger;
import org.apache.commons.lang.SystemUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.print.Paper;

import static com.Helloy.cowCannon.CowCannon.*;
import static org.bukkit.Bukkit.getServer;

public class Methods {


    public void GetCommands() {
        JavaPlugin.getPlugin(CowCannon.class).getCommand("cow").setExecutor(new CowCommand());
      JavaPlugin.getPlugin(CowCannon.class).getCommand("cow").setTabCompleter(new CowCommand());
      JavaPlugin.getPlugin(CowCannon.class).getCommand("butterfly").setExecutor(new ButterflyCommand());
      JavaPlugin.getPlugin(CowCannon.class).getCommand("butterfly").setTabCompleter(new ButterflyCommand());
       JavaPlugin.getPlugin(CowCannon.class).getCommand("displayentity").setExecutor(new DisplayEntityCommand());
      JavaPlugin.getPlugin(CowCannon.class).getCommand("displayentity").setTabCompleter(new DisplayEntityCommand());
       JavaPlugin.getPlugin(CowCannon.class).getCommand("psycho").setExecutor(new PsychoCommand());
       JavaPlugin.getPlugin(CowCannon.class).getCommand("psycho").setTabCompleter(new PsychoCommand());
         JavaPlugin.getPlugin(CowCannon.class).getCommand("perm").setExecutor(new PermCommand());
         JavaPlugin.getPlugin(CowCannon.class).getCommand("perm").setTabCompleter(new PermCommand());
    }

    public void RegisterListeners() {
        getServer().getPluginManager().registerEvents(new EntityListener(), plugin);
        if (isPaper()) {
            getServer().getPluginManager().registerEvents(new PaperChatListener(), plugin);
        }
        if (isSpigot()) {
            getServer().getPluginManager().registerEvents(new SpigotChatListener(), plugin);
        }
        float javaVersion = SystemUtils.getJavaVersion();
        Bukkit.getLogger().info("Java version: " + javaVersion);
    }
}

