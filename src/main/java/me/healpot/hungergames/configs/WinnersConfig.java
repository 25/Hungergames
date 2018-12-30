package me.healpot.hungergames.configs;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;

@Data
@EqualsAndHashCode(callSuper = false)
public class WinnersConfig extends BaseConfig {
    private HashMap<Integer, Integer> prizesForPlacing = new HashMap<Integer, Integer>();

    public WinnersConfig() {
        super("prizes");
        prizesForPlacing.put(1, 10);
        prizesForPlacing.put(2, 5);
        prizesForPlacing.put(3, 3);
    }

}
