package fr.mana.terradiscovery.commands;

import fr.mana.terradiscovery.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.List;

public class Discovery implements CommandExecutor {
    private TerraDiscovery main;

    public Discovery(TerraDiscovery main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args[0].equalsIgnoreCase("reload")){
            main.reloadConfig();
            sender.sendMessage("§eConfiguration rechargée !");
        }
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelpMessages(player);
        } else {
            String subCommand = args[0].toLowerCase();

            switch (subCommand) {
                case "list":
                    listWarps(player);
                    break;
                case "create":
                    if (args.length < 2) {
                        sendHelpMessages(player);
                        break;
                    }

                    String warpToCreate = args[1].toLowerCase();
                    createWarp(warpToCreate, player.getLocation());
                    player.sendMessage("§eLe warp " + warpToCreate + " a été créé à votre position actuelle !");
                    break;
                case "remove":
                    if (args.length < 2) {
                        sendHelpMessages(player);
                        break;
                    }

                    String warpToRemove = args[1].toLowerCase();
                    boolean removed = removeWarp(warpToRemove);
                    if (removed) {
                        player.sendMessage("§eLa zone " + warpToRemove + " a été supprimée !");
                    } else {
                        player.sendMessage("§cLa zone spécifiée n'existe pas !");
                    }
                    break;
                case "setspawn":
                    if (args.length < 2) {
                        sendHelpMessages(player);
                        break;
                    }
                    String warpToSet = args[1].toLowerCase();
                    setSpawnForWarp(warpToSet, player.getLocation());
                    player.sendMessage("§eLa position du spawn de la zone " + warpToSet + " a été définie à votre position actuelle !");
                    break;

                default:
                    sendHelpMessages(player);
                    break;
            }
        }
        return true;
    }

    private void createWarp(String warpName, Location location) {
        ConfigurationSection warpSection = main.getConfig().getConfigurationSection("warps." + warpName);

        if (warpSection == null) {
            warpSection = main.getConfig().createSection("warps." + warpName);
        }

        warpSection.set("display-name", warpName);
        warpSection.set("permission", "warps." + warpName);
        warpSection.set("cooldown", 5);
        warpSection.set("region", warpName + "_rg");
        warpSection.set("x", location.getX());
        warpSection.set("y", location.getY());
        warpSection.set("z", location.getZ());
        warpSection.set("pitch", location.getPitch());
        warpSection.set("yaw", location.getYaw());

        main.saveConfig();
    }

    private boolean removeWarp(String warpName) {
        ConfigurationSection warpsSection = main.getConfig().getConfigurationSection("warps");

        if (warpsSection == null) {
            return false;
        }

        if (!warpsSection.contains(warpName)) {
            return false;
        }

        warpsSection.set(warpName, null);
        main.saveConfig();
        return true;
    }

    private void setSpawnForWarp(String warpName, Location location) {
        ConfigurationSection warpSection = main.getConfig().getConfigurationSection("warps." + warpName);

        if (warpSection == null) {
            return;
        }

        warpSection.set("x", location.getX());
        warpSection.set("y", location.getY());
        warpSection.set("z", location.getZ());
        warpSection.set("pitch", location.getPitch());
        warpSection.set("yaw", location.getYaw());

        main.saveConfig();
    }

    private void listWarps(Player player) {
        ConfigurationSection warpsSection = main.getConfig().getConfigurationSection("warps");

        if (warpsSection == null || warpsSection.getKeys(false).isEmpty()) {
            player.sendMessage("§cAucune zone trouvée.");
            return;
        }

        player.sendMessage("§7Liste des zones :");

        for (String warpName : warpsSection.getKeys(false)) {
            ConfigurationSection warpSection = warpsSection.getConfigurationSection(warpName);
            if (warpSection == null) {
                continue;
            }

            String displayName = warpSection.getString("display-name");
            String region = warpSection.getString("region");

            if (displayName == null || region == null) {
                continue;
            }

            player.sendMessage("§7" + ChatColor.translateAlternateColorCodes('&', displayName) + "§e (Région: " + region + ")");
        }
    }

    private void sendHelpMessages(Player player) {
        List<String> lines = main.getConfig().getStringList("messages.help");
        for (String line : lines) {
            String lineEdited = ChatColor.translateAlternateColorCodes('&', line);
            player.sendMessage(lineEdited);
        }
    }
}