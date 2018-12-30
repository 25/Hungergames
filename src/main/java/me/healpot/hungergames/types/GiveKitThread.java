package me.healpot.hungergames.types;

import me.healpot.hungergames.configs.MySqlConfig;

import java.sql.*;
import java.util.Date;

public class GiveKitThread extends Thread {
    private Connection con = null;
    private String kitName;
    private String playerName;
    private Timestamp timestamp;
    private String uuid;

    public GiveKitThread(String player, String uuid, String kit) {
        this.uuid = uuid;
        kitName = kit;
        playerName = player;
        timestamp = new Timestamp(new Date().getTime());
    }

    public GiveKitThread(String player, String uuid, String kit, Timestamp timestamp) {
        this.uuid = uuid;
        this.kitName = kit;
        this.playerName = player;
        this.timestamp = timestamp;
    }

    public void mySqlConnect() {
        MySqlConfig config = HungergamesApi.getConfigManager().getMySqlConfig();
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            String conn = "jdbc:mysql://" + config.getMysql_host() + "/" + config.getMysql_database();
            con = DriverManager.getConnection(conn, config.getMysql_username(), config.getMysql_password());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void mySqlDisconnect() {
        try {
            this.con.close();
        } catch (SQLException ex) {
        } catch (NullPointerException ex) {
        }
    }

    public void run() {
        if (!HungergamesApi.getConfigManager().getMySqlConfig().isMysqlKitsEnabled())
            return;
        mySqlConnect();
        try {
            PreparedStatement stmt = con
                    .prepareStatement("INSERT INTO HGKits (`uuid`, `Name`, `KitName`, `Date`) VALUES (?,?,?,?)");
            stmt.setString(1, uuid);
            stmt.setString(2, playerName);
            stmt.setString(3, kitName);
            stmt.setTimestamp(4, timestamp);
            stmt.execute();
            stmt.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        mySqlDisconnect();
    }
}
