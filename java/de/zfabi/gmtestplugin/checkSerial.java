package de.zfabi.gmtestplugin;


import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class checkSerial implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player))
            return false;
        if(!(args.length == 1)) {
            sender.sendMessage("§cBitte benutze /checkserial <Seriennummer>");
            return false;
        }

        Player player = (Player) sender;
        String sn = args[0];
        if(sn.length() != 6) {
            player.sendMessage("§cDie Seriennummer muss 6 Zeichen lang sein.");
            return false;
        }

        if(!Data.serialNumbers().contains(sn)) {
            player.sendMessage("§cDiese Seriennummer existiert nicht.");
            return false;
        }

        String uuid = Data.uuid.get(sn).toString();
        Player t = Bukkit.getOfflinePlayer(UUID.fromString(uuid)).getPlayer();
        long created = Data.created.get(sn);
        Date d = new Date(created);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

        player.sendMessage("§aDie Seriennummer §a§l" + sn + "§a wurde von §a§l" + t.getName() + "§a am §a§l" + sdf.format(d) + " §aerstellt.");
        return false;
    }
}