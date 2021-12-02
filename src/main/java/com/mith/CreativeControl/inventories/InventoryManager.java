package com.mith.CreativeControl.inventories;

import com.mith.CreativeControl.CreativeControl;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;


public class InventoryManager implements Listener  {

        private static CreativeControl plugin = CreativeControl.getInstance();

        @EventHandler(priority = EventPriority.HIGH)
        public void onGameModeChange(PlayerGameModeChangeEvent event) throws IOException {

            if (!plugin.getConfig().getBoolean("inventoryAccess.enabled", true)) {
                return;
            }

            //dont do anything with creative control admin
            if (event.getPlayer().hasPermission("creativecontrol.admin")) {
                return;
            }

            if(event.getNewGameMode() == GameMode.CREATIVE)
            {
                List<String> blockedWorlds = plugin.getConfig().getStringList("blockedWorlds");
                if(blockedWorlds.contains(event.getPlayer().getWorld().getName()))
                {
                    event.getPlayer().sendMessage(ChatColor.RED + "Creative is not enabled in this world, your gamemode has not been changed.");
                    event.setCancelled(true);
                    return;
                }
            }

            switch (event.getNewGameMode()) {
                case CREATIVE: {
                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                        saveInventory(event.getPlayer(), "survival");
                    }
                    restoreInventory(event.getPlayer(), "creative");
                    break;
                }
                case SURVIVAL: {
                    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                        saveInventory(event.getPlayer(), "creative");
                    }
                    restoreInventory(event.getPlayer(), "survival");
                    break;
                }
                case SPECTATOR:
                case ADVENTURE: {
                    if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
                        saveInventory(event.getPlayer(), "creative");
                    }
                    if (event.getPlayer().getGameMode() == GameMode.SURVIVAL) {
                        saveInventory(event.getPlayer(), "survival");
                    }
                    break;
                }
            }
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        public void playerDie(PlayerDeathEvent e) {

            if(e.getEntity().getGameMode() != GameMode.CREATIVE)
            {
                return;
            }

            e.setKeepInventory(true);
            e.setKeepLevel(true);
            e.setDroppedExp(0);
            e.getDrops().clear();
        }


        public void saveInventory(Player p, String gameMode) throws IOException {
            File f = new File(plugin.getDataFolder() +"/PlayersInventory/", gameMode + "_" + p.getUniqueId() + ".yml");
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            c.set("inventory.xp", p.getExp());
            c.set("inventory.armor", p.getInventory().getArmorContents());
            c.set("inventory.content", p.getInventory().getContents());
            c.save(f);
            p.getInventory().clear();
        }

        public void restoreInventory(Player p, String gameMode) throws IOException {


            File f = new File(plugin.getDataFolder() +"/PlayersInventory/", gameMode + "_" + p.getUniqueId() + ".yml");
            if(!f.exists()){
                return;
            }
            FileConfiguration c = YamlConfiguration.loadConfiguration(f);
            double d = (double) c.get("inventory.xp");
            float exp = (float)d;
            p.setExp(exp);
            ItemStack[] content = ((List<ItemStack>) c.get("inventory.armor")).toArray(new ItemStack[0]);
            p.getInventory().setArmorContents(content);
            content = ((List<ItemStack>) c.get("inventory.content")).toArray(new ItemStack[0]);
            p.getInventory().setContents(content);
        }
    }



