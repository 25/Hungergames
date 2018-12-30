package me.healpot.hungergames.managers;

import lombok.Data;
import me.healpot.hungergames.configs.*;


@Data
public class ConfigManager {
    private FeastConfig feastConfig = new FeastConfig();
    private LoggerConfig loggerConfig = new LoggerConfig();
    private MainConfig mainConfig = new MainConfig();
    private MySqlConfig mySqlConfig = new MySqlConfig();
    private TranslationConfig translationsConfig = new TranslationConfig();
    private WinnersConfig winnersConfig = new WinnersConfig();

    public void loadConfigs() {
        for (BaseConfig config : new BaseConfig[]{mainConfig, translationsConfig, loggerConfig, feastConfig, mySqlConfig,
                winnersConfig, new DeathConfig()}) {
            try {
                config.load();
                config.loadConfig();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
