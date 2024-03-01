package de.zfabi.gmtestplugin;

import com.google.common.cache.AbstractCache;
import com.google.gson.JsonArray;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class db {
    public static JsonArray serialNumbers;
    private static String host;
    private static String port;
    private static String database;
    private static String username;
    private static String password;
    private static Connection con;

    public static void connect() {
        if (!isCon()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                Bukkit.getConsoleSender().sendMessage("§aMYSQL Connected!");

                con.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS `gm` (id int AUTO_INCREMENT, uuid VARCHAR(255), time LONG, seriennummer VARCHAR(255), PRIMARY KEY (id))");
            } catch (SQLException e) {
                Bukkit.getConsoleSender().sendMessage("§cÜberprüfe die MySQL Zugangsdaten!");
            }
        }
    }

    public static void disconnect() {
        if(isCon()) {
            try {
                con.close();
                Bukkit.getConsoleSender().sendMessage("§c MYSQL Disconnected!");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static boolean isCon() {
        return con != null;
    }

    public static ResultSet getResult(String qry) {
        if(!isCon()) return null;
        try {
            return con.createStatement().executeQuery(qry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void update(String qry) {
        if(!isCon()) return;
        try {
            con.createStatement().executeUpdate(qry);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static File getConfig() {
        return new File("plugins/gmtestplugin", "mysql.yml");
    }

    public static FileConfiguration getConfiguration() {
        return YamlConfiguration.loadConfiguration(getConfig());
    }

    public static void setStandart() {
        FileConfiguration cfg = getConfiguration();
        cfg.options().copyDefaults(true);
        cfg.addDefault("host", "localhost");
        cfg.addDefault("port", "3306");
        cfg.addDefault("database", "database");
        cfg.addDefault("username", "root");
        cfg.addDefault("password", "password");
        try {
            cfg.save(getConfig());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void readConfig() {
        FileConfiguration cfg = getConfiguration();
        host = cfg.getString("host");
        port = cfg.getString("port");
        database = cfg.getString("database");
        username = cfg.getString("username");
        password = cfg.getString("password");
    }

}
