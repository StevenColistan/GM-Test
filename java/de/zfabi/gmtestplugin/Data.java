package de.zfabi.gmtestplugin;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Data {

    public static List<String> serialNumbers = new ArrayList<>();
    public static HashMap<String, Integer> counts = new HashMap<>();
    public static HashMap<String, Long> created = new HashMap<>();
    public static HashMap<String, UUID> uuid = new HashMap<>();

    public static void created() {
        ResultSet rs = db.getResult("SELECT * FROM `gm`");
        try {
            while (rs != null && rs.next()) {
                created.put(rs.getString("seriennummer"), rs.getLong("created"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void user() {
        ResultSet rs = db.getResult("SELECT * FROM `gm`");
        try {
            while (rs != null && rs.next()) {
                uuid.put(rs.getString("seriennummer"), UUID.fromString(rs.getString("uuid")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> serialNumbers() {
        ResultSet rs = db.getResult("SELECT * FROM `gm`");
        try {
            while (rs != null && rs.next()) {
                serialNumbers.add(rs.getString("seriennummer"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return serialNumbers;
    }
}