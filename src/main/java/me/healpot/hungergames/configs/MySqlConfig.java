package me.healpot.hungergames.configs;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

@Data
@EqualsAndHashCode(callSuper = false)
public class MySqlConfig extends BaseConfig {

    private boolean buyKitMenuEnabled = true;
    private String mysql_database = "database";
    private String mysql_host = "localhost";
    private String mysql_password = "password";
    private String mysql_username = "root";
    private boolean mysqlEnabled;
    private boolean mysqlKitsEnabled = true;
    private String rankingFormula = "(((Wins * 5) + (Kills / 10) / (Losses / 10)) + ((Wins + Losses) / 50)) + Killstreak";
    private boolean statsEnabled = true;
    private boolean useUUIDs = true;

    public MySqlConfig() {
        super("mysql");
        File file = new File("plugins/Hungergames/config.yml");
        if (file.exists()) {
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
            buyKitMenuEnabled = config.getBoolean("mysqlEnabled");
            statsEnabled = config.getBoolean("mysqlEnabled");
            mysqlEnabled = config.getBoolean("mysqlEnabled");
            mysqlKitsEnabled = config.getBoolean("mysqlEnabled");
            config.set("mysqlEnabled", null);
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isBuyKitMenuEnabled() {
        return isMysqlKitsEnabled() && buyKitMenuEnabled;
    }

    @Deprecated
    public boolean isMysqlEnabled() {
        return mysqlEnabled && (statsEnabled || buyKitMenuEnabled || mysqlKitsEnabled);
    }

    public boolean isMysqlKitsEnabled() {
        return mysqlEnabled && mysqlKitsEnabled;
    }

    public boolean isStatsEnabled() {
        return mysqlEnabled && statsEnabled;
    }
}
