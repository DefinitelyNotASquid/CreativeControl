package com.mith.CreativeControl.listeners;

import com.mith.CreativeControl.CreativeControl;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.bukkit.entity.EntityType.ITEM_FRAME;


public class PlayerListener implements Listener {

    private static CreativeControl plugin = CreativeControl.getInstance();

    public static  List<Material> eggs = new ArrayList<Material>(Arrays.asList(
            Material.AXOLOTL_SPAWN_EGG,
            Material.BAT_SPAWN_EGG,
            Material.BEE_SPAWN_EGG,
            Material.BLAZE_SPAWN_EGG,
            Material.CAVE_SPIDER_SPAWN_EGG,
            Material.CAT_SPAWN_EGG,
            Material.CHICKEN_SPAWN_EGG,
            Material.COD_SPAWN_EGG,
            Material.COW_SPAWN_EGG,
            Material.CREEPER_SPAWN_EGG,
            Material.DOLPHIN_SPAWN_EGG,
            Material.DONKEY_SPAWN_EGG,
            Material.DROWNED_SPAWN_EGG,
            Material.ELDER_GUARDIAN_SPAWN_EGG,
            Material.ENDERMAN_SPAWN_EGG,
            Material.ENDERMITE_SPAWN_EGG,
            Material.EVOKER_SPAWN_EGG,
            Material.FOX_SPAWN_EGG,
            Material.GHAST_SPAWN_EGG,
            Material.GLOW_SQUID_SPAWN_EGG,
            Material.GOAT_SPAWN_EGG,
            Material.GUARDIAN_SPAWN_EGG,
            Material.HORSE_SPAWN_EGG,
            Material.HUSK_SPAWN_EGG,
            Material.LLAMA_SPAWN_EGG,
            Material.MAGMA_CUBE_SPAWN_EGG,
            Material.MOOSHROOM_SPAWN_EGG,
            Material.MULE_SPAWN_EGG,
            Material.OCELOT_SPAWN_EGG,
            Material.PANDA_SPAWN_EGG,
            Material.PARROT_SPAWN_EGG,
            Material.PHANTOM_SPAWN_EGG,
            Material.PIG_SPAWN_EGG,
            Material.PILLAGER_SPAWN_EGG,
            Material.POLAR_BEAR_SPAWN_EGG,
            Material.PUFFERFISH_SPAWN_EGG,
            Material.RABBIT_SPAWN_EGG,
            Material.RAVAGER_SPAWN_EGG,
            Material.SALMON_SPAWN_EGG,
            Material.SHEEP_SPAWN_EGG,
            Material.SHULKER_SPAWN_EGG,
            Material.SILVERFISH_SPAWN_EGG,
            Material.SKELETON_HORSE_SPAWN_EGG,
            Material.SKELETON_SPAWN_EGG,
            Material.SLIME_SPAWN_EGG,
            Material.SPIDER_SPAWN_EGG,
            Material.SQUID_SPAWN_EGG,
            Material.STRAY_SPAWN_EGG,
            Material.TRADER_LLAMA_SPAWN_EGG,
            Material.TROPICAL_FISH_SPAWN_EGG,
            Material.TURTLE_SPAWN_EGG,
            Material.VEX_SPAWN_EGG,
            Material.VILLAGER_SPAWN_EGG,
            Material.VINDICATOR_SPAWN_EGG,
            Material.WANDERING_TRADER_SPAWN_EGG,
            Material.WITCH_SPAWN_EGG,
            Material.WITHER_SKELETON_SPAWN_EGG,
            Material.WOLF_SPAWN_EGG,
            Material.ZOMBIE_HORSE_SPAWN_EGG,
            Material.ZOGLIN_SPAWN_EGG,
            Material.HOGLIN_SPAWN_EGG,
            Material.PIGLIN_SPAWN_EGG,
            Material.STRIDER_SPAWN_EGG,
            Material.ZOMBIFIED_PIGLIN_SPAWN_EGG,
            Material.ZOMBIE_SPAWN_EGG,
            Material.ZOMBIE_VILLAGER_SPAWN_EGG
    ));


    @EventHandler
    public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent changedWorldEvent){
        Player cplayer = changedWorldEvent.getPlayer();

        if(cplayer.getGameMode() != GameMode.CREATIVE){
            return;
        }

        if (cplayer.hasPermission("creativecontrol.admin")) {
            return;
        }

        List<String> blockedWorlds = plugin.getConfig().getStringList("blockedWorlds");
        if(blockedWorlds.contains(cplayer.getWorld().getName()))
        {
            cplayer.sendMessage(ChatColor.RED + "Creative is not enabled in this world, it has been set to survival.");
            cplayer.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent joinEvent){
        Player cplayer = joinEvent.getPlayer();

        if(cplayer.getGameMode() != GameMode.CREATIVE){
            return;
        }

        if (cplayer.hasPermission("creativecontrol.admin")) {
            return;
        }

        List<String> blockedWorlds = plugin.getConfig().getStringList("blockedWorlds");
        if(blockedWorlds.contains(cplayer.getWorld().getName()))
        {
            cplayer.sendMessage(ChatColor.RED + "Creative is not enabled in this world, it has been set to survival.");
            cplayer.setGameMode(GameMode.SURVIVAL);
        }
    }

    @EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onVoidDamage(EntityDamageEvent event) {
        if (event.getEntityType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player)event.getEntity();

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDamageByCreativePlayer(EntityDamageByEntityEvent event) {
        if (!plugin.getConfig().getBoolean("blockThisAction.pvp", true)) {
            return;
        }
        if (event.getDamager().getType() != EntityType.PLAYER || event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }
        Player cPlayer = (Player) event.getDamager();

        if (cPlayer.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (cPlayer.hasPermission("creativecontrol.admin")) {
            return;
        }
        cPlayer.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerHitAMob(EntityDamageByEntityEvent event) {
        if (!plugin.getConfig().getBoolean("blockThisAction.pve", true)) {
            return;
        }
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player cPlayer = (Player) event.getDamager();

        if (cPlayer.getGameMode() != GameMode.CREATIVE) {
            return;
        }

        if (cPlayer.hasPermission("creativecontrol.admin")) {
            return;
        }

        cPlayer.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void onCreativePlayerDropItem(PlayerDropItemEvent event) {
        if (!plugin.getConfig().getBoolean("blockThisAction.dropItems", true)) {
            return;
        }
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void onCreativeChestOpen(InventoryOpenEvent event) {
        if (!plugin.getConfig().getBoolean("block.chests", true)) {
            return;
        }

        Player player = (Player) event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        if (!(event.getInventory().getType() == InventoryType.PLAYER)) {
            player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onCreativePlayerPickupItem(EntityPickupItemEvent event) {

        if (!plugin.getConfig().getBoolean("blockThisAction.itemPickup", true)) {
            return;
        }

        if (event.getEntity().getType() != EntityType.PLAYER) {
            return;
        }

        Player player = (Player) event.getEntity();

        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onCreativeBlockPlace(BlockPlaceEvent event) {
        if (!plugin.getConfig().getBoolean("creative-place-blacklist.enabled", true)) {
            return;
        }
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        List<String> blacklistedBlocks = plugin.getConfig().getStringList("creative-place-blacklist.blacklist");
        Block block = event.getBlock();
        String m = block.getType().name();
        if (!blacklistedBlocks.contains(m)) {
            return;
        }
        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void onCreativeBlockBreak(BlockBreakEvent event) {
        if (!plugin.getConfig().getBoolean("creative-break-blacklist.enabled", true)) {
            return;
        }
        Player player = event.getPlayer();

        if (player.getGameMode() != GameMode.CREATIVE) {
            return;
        }
        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }

        List<String> blacklistedBlocks = plugin.getConfig().getStringList("creative-break-blacklist.blacklist");
        Block block = event.getBlock();
        String m = block.getType().name();
        if (!blacklistedBlocks.contains(m)) {
            return;
        }
        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void spawnerEgg(PlayerInteractEvent e){

        if (!plugin.getConfig().getBoolean("blockThisAction.spawnEggs", true)) {
            return;
        }
        Player player = e.getPlayer();

        if(player.getGameMode() != GameMode.CREATIVE){
            return;
        }

        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        ItemStack i = e.getItem();

        if(i == null)
        {
            return;
        }

        if(!eggs.contains(i.getType()))
        {
            return;
        }

        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        e.setCancelled(true);

    }

    @EventHandler
    public void onItemFrameUse(PlayerInteractEntityEvent event) {

        if (!(event.getRightClicked().getType() == ITEM_FRAME)) {
            return;
        }

        if (!plugin.getConfig().getBoolean("itemFrame.enabled", true)) {
            return;
        }
        Player player = event.getPlayer();

        if(player.getGameMode() != GameMode.CREATIVE){
            return;
        }

        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        System.out.println("Item interaction: " + player.getInventory().getItemInMainHand().getData().getItemType().toString());
        List<String> whitelisteditems = plugin.getConfig().getStringList("itemFrame.whitelist");

        if (whitelisteditems.contains(player.getInventory().getItemInMainHand().getData().getItemType().toString())) {
            return;
        }
        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        event.setCancelled(true);
    }

    @EventHandler
    public void onPotionThrow(ProjectileLaunchEvent e) {
        if(!(e.getEntity().getShooter() instanceof Player)){
            return;
        }

        Player player = (Player) e.getEntity().getShooter();
        if(player.getGameMode() != GameMode.CREATIVE){
            return;
        }
        if (!plugin.getConfig().getBoolean("blockThisAction.potions", true)) {
            return;
        }
        if (player.hasPermission("creativecontrol.admin")) {
            return;
        }
        player.sendMessage(ChatColor.RED + "You cannot perform this action in creative mode. Please change your mode to survival.");
        e.setCancelled(true);
    }
}
