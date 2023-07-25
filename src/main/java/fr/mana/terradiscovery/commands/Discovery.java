package fr.mana.terradiscovery.commands;

import fr.mana.terradiscovery.*;
import org.bukkit.command.*;
import org.bukkit.configuration.*;
import org.bukkit.entity.*;

import java.util.*;

public class Discovery implements CommandExecutor {
    private TerraDiscovery main;

    public Discovery(TerraDiscovery main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            sendHelpMessages(player);
        } else {
            String subCommand = args[0].toLowerCase();

            switch (subCommand) {
                case "reload":
                    main.reloadConfig();
                    player.sendMessage("§eConfiguration rechargée !");
                    break;
                case "list":
                    listWarps(player);
                    break;
                default:
                    sendHelpMessages(player);
                    break;
            }
        }
        return true;
    }


    public void listWarps(Player player) {
        ConfigurationSection warpsSection = main.getConfig().getConfigurationSection("warps");

        if (warpsSection == null || warpsSection.getKeys(false).isEmpty()) {
            player.sendMessage("§cAucun warp trouvé.");
            return;
        }

        player.sendMessage("§7Liste des warps :");

        for (String warpName : warpsSection.getKeys(false)) {

            String displayName = warpsSection.getString(warpName + ".display-name").replace("&","§");
            String region = warpsSection.getString(warpName + ".region");

            if (region == null) {
                continue;
            }

            player.sendMessage("§7" + displayName + "§e(Région: " + region + ")");
        }
    }

    public void sendHelpMessages(Player player){
        List<String> lines = main.getConfig().getStringList("messages.help");
        for(String line : lines){
            String lineEdited = line.replace("&","§");
            player.sendMessage(lineEdited);
        }
    }



}
