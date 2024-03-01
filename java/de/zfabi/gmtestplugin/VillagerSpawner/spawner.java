package de.zfabi.gmtestplugin.VillagerSpawner;

import de.zfabi.gmtestplugin.GMTestPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.metadata.FixedMetadataValue;
import java.util.List;
public class spawner implements CommandExecutor, TabExecutor {
@Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (!(sender instanceof Player)) {
                sender.sendMessage("Nur Spieler können diesen Command benutzten");

                return true;
            }

            Player player = (Player) sender;
            Villager villager = (Villager) player.getWorld().spawnEntity(player.getLocation(), EntityType.VILLAGER);

            villager.setMetadata("WeaponSmith", new FixedMetadataValue(GMTestPlugin.getInstance(), true));
            villager.setCustomName(ChatColor.BLUE + "Weapon Smith");
            villager.setCustomNameVisible(true);

            player.sendMessage("Villager spawned!");
            return false;

    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String s, String[] strings) {
        return null;
    }
}
