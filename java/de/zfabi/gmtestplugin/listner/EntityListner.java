package de.zfabi.gmtestplugin.listner;

import de.zfabi.gmtestplugin.Data;
import de.zfabi.gmtestplugin.GMTestPlugin;
import de.zfabi.gmtestplugin.db;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;

import static de.zfabi.gmtestplugin.db.serialNumbers;

public class EntityListner implements Listener {
//    public Set<String> serialNumbers = new HashSet<>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onEntityRightClickLate(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        if (entity instanceof Villager && entity.hasMetadata("WeaponSmith")) {
            Villager villager = (Villager) entity;
            event.setCancelled(true);
            villager.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 9600, 100));

            openMenu(player);
            player.setMetadata("OpenedWeaponSmith", new FixedMetadataValue(GMTestPlugin.getInstance(), "Preferences Menu"));
        }


    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        Entity entity = event.getEntity();

        if (event.getEntity().getType().equals(EntityType.VILLAGER)) {
            if (entity instanceof Villager && entity.hasMetadata("WeaponSmith")) {
                if (event.getDamager() instanceof Player) {
                    Player player = (Player) event.getDamager();
                    event.setCancelled(true);
                    player.sendActionBar("§cYou cannot hit villagers!");
                }
            }
        }
    }

    private void openMenu(Player player) {
        Inventory menu = Bukkit.createInventory(player, 9 * 3, "Weapon Smith");

        ItemStack Kuchen = new ItemStack(Material.PUMPKIN_PIE);
        ItemMeta clearWeatherMeta = Kuchen.getItemMeta();
        clearWeatherMeta.setDisplayName(ChatColor.YELLOW + "Kuchen");
        Kuchen.setItemMeta(clearWeatherMeta);

        menu.setItem(13, Kuchen);

        player.openInventory(menu);


    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        if (player.hasMetadata("OpenedWeaponSmith")) {
            event.setCancelled(true);

            if (event.getSlot() == 13) {
                ItemStack item = new ItemStack(Material.PUMPKIN_PIE);


                String serialNumber = generateSerialNumber();
                ItemMeta meta = item.getItemMeta();
                meta.setLore(Collections.singletonList("§3Serial Number: " + serialNumber));
                item.setItemMeta(meta);

                db.update("INSERT INTO `gm` (`uuid`, `time`, `seriennummer`) VALUES ('" + player.getUniqueId().toString() + "', '" + (long) System.currentTimeMillis() + "', '" + serialNumber + "')");
                Data.serialNumbers.add(serialNumber);
                Data.uuid.put(serialNumber, player.getUniqueId());
                Data.created.put(serialNumber, (long) System.currentTimeMillis());
                player.getInventory().addItem(item);
                player.closeInventory();

            }
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();

        if (player.hasMetadata("OpenedWeaponSmith"))
            player.removeMetadata("OpenedWeaponSmith", GMTestPlugin.getInstance());
    }

    private String generateSerialNumber() {
        return String.valueOf((int) (Math.random() * 1000000));
    }
}
