package fr.mana.terradiscovery.events;

import fr.mana.terradiscovery.TerraDiscovery;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import java.util.*;

public class WarpUnlockListener implements Listener {
    private TerraDiscovery main;

    public WarpUnlockListener(TerraDiscovery main) {
        this.main = main;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        ApplicableRegionSet regions = Objects.requireNonNull(WorldGuard.getInstance().getPlatform().getRegionContainer().get(BukkitAdapter.adapt(player.getWorld()))).getApplicableRegions(BukkitAdapter.asBlockVector(player.getLocation()));

        for (ProtectedRegion region : regions) {
            String regionName = region.getId();
            if (main.getConfig().isConfigurationSection("warps."+regionName)) {
                String warpPermission = main.getConfig().getString("warps." + regionName + ".permission");
                String warpDisplayName = Objects.requireNonNull(main.getConfig().getString("warps." + regionName + ".display-name")).replace("&","§");
                assert warpPermission != null;
                if (!player.hasPermission(warpPermission)) {
                    unlockWarp(player, regionName);
                }
            }
        }

    }

    public void unlockWarp(Player player, String regionName){
        String warpDisplayName = Objects.requireNonNull(main.getConfig().getString("warps." + regionName + ".display-name")).replace("&","§");
        String warpPermission = main.getConfig().getString("warps." + regionName + ".permission");
        addWarpToPlayer(player, warpPermission, warpDisplayName);
    }
    public void addWarpToPlayer(Player player, String warpPermission, String warpDisplayName){
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user "+player.getDisplayName()+" permission set "+warpPermission+" true");
        sendUnlockTitle(player, warpDisplayName);
        sendUnlockMessage(player, warpDisplayName);
        playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
    }

    public void sendUnlockTitle(Player player, String warpDisplayName){
        String titleFirstLine = Objects.requireNonNull(main.getConfig().getString("titles.unlocked.firstLine")).replace("&","§").replace("%warpDisplayName%", warpDisplayName);
        String titleSecondLine = Objects.requireNonNull(main.getConfig().getString("titles.unlocked.secondLine")).replace("&","§").replace("%warpDisplayName%", warpDisplayName);
        player.sendTitle(titleFirstLine,titleSecondLine, 10, 70, 20);
    }

    public void sendUnlockMessage(Player player, String warpDisplayName){
        List<String> lines = main.getConfig().getStringList("messages.warps.unlocked");
        for (String line : lines){
            String lineEdited = line.replace("&","§");
            player.sendMessage(lineEdited);
        }
    }

    public void playSound(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }
}