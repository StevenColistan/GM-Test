package de.zfabi.gmtestplugin;

import de.zfabi.gmtestplugin.listner.Scheduler;
import org.bukkit.plugin.java.JavaPlugin;
import de.zfabi.gmtestplugin.listner.EntityListner;

import de.zfabi.gmtestplugin.VillagerSpawner.spawner;


public class GMTestPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        db mySQL = new db();
        mySQL.setStandart();
        mySQL.readConfig();
        mySQL.connect();

        new Scheduler(this).startTask();

        getLogger().info("VillagerSpawner has been enabled!");
        getLogger().info("SerialNumberPlugin has been enabled!");

        getServer().getPluginManager().registerEvents(new EntityListner(), this);


        getCommand("checkserial").setExecutor(new checkSerial());
        getCommand("spawnvillager").setExecutor(new spawner());
    }

    @Override
    public void onDisable() {
        db mySQL = new db();
        getLogger().info("SerialNumberPlugin has been disabled!");
        getLogger().info("VillagerSpawner has been disabled!");

        mySQL.disconnect();
        // saveSerialNumbers();
    }

    public static GMTestPlugin getInstance() {
        return getPlugin(GMTestPlugin.class);
    }

}