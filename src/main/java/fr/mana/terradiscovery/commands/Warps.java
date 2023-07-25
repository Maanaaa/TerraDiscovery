package fr.mana.terradiscovery.commands;

import fr.mana.terradiscovery.TerraDiscovery;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Warps implements CommandExecutor {
    private TerraDiscovery main;

    public Warps(TerraDiscovery main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Inventory menu = main.getServer().createInventory(player, 27, "§b§lMenu des zones");

            for (String warpName : Objects.requireNonNull(main.getConfig().getConfigurationSection("warps")).getKeys(false)) {
                String displayName = Objects.requireNonNull(main.getConfig().getString("warps." + warpName + ".display-name")).replace("&","§");
                String permission = main.getConfig().getString("warps." + warpName + ".permission");

                Material itemMaterial;
                String itemDisplayName;

                // Vérifie si le joueur a la permission pour ce warp
                assert permission != null;
                if (player.hasPermission(permission)) {
                    itemMaterial = Material.COMPASS;
                    itemDisplayName = "§a" + displayName;
                } else {
                    itemMaterial = Material.RED_STAINED_GLASS;
                    itemDisplayName = "§c" + displayName + " §7(§cÀ découvrir§7)";
                }

                ItemStack item = new ItemStack(itemMaterial);
                ItemMeta meta = item.getItemMeta();
                assert meta != null;
                meta.setDisplayName(itemDisplayName);

                List<String> lore = new ArrayList<>();
                lore.add("§bCliquez pour vous téléporter");
                meta.setLore(lore);
                item.setItemMeta(meta);
                menu.addItem(item);
            }

            player.openInventory(menu);
        } else {
            sender.sendMessage("§cCette commande doit être exécutée par un joueur !");
        }
        return true;
    }

}
