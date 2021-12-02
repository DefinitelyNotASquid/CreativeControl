package com.mith.CreativeControl;

import com.mith.CreativeControl.commands.CommandReload;
import com.mith.CreativeControl.inventories.InventoryManager;
import com.mith.CreativeControl.listeners.PlayerListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class CreativeControl extends JavaPlugin {

    private static CreativeControl instance;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        registerListeners();
        getCommand("ccreload").setExecutor(new CommandReload());
    }


    private void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new InventoryManager(), this);
    }

    public static CreativeControl getInstance() {
        return instance;
    }
}
