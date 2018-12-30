package me.healpot.hungergames.listeners;

import me.healpot.hungergames.Hungergames;
import me.healpot.hungergames.configs.MainConfig;
import me.healpot.hungergames.configs.TranslationConfig;
import me.healpot.hungergames.managers.PlayerManager;
import me.healpot.hungergames.types.Gamer;
import me.healpot.hungergames.types.HungergamesApi;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.entity.*;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.server.ServerListPingEvent;

import java.util.Iterator;
import java.util.Random;

public class GeneralListener implements Listener {

    private TranslationConfig cm = HungergamesApi.getConfigManager().getTranslationsConfig();
    private MainConfig config = HungergamesApi.getConfigManager().getMainConfig();
    private Hungergames hg = HungergamesApi.getHungergames();
    private PlayerManager pm = HungergamesApi.getPlayerManager();

    @EventHandler
    public void ignite(final BlockIgniteEvent event) {
        if (hg.currentTime < 0 && config.isFireSpreadPreGame()) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Player) {
            Gamer gamer = pm.getGamer(entity);
            if (gamer == null || hg.currentTime <= config.getTimeForInvincibility() || !hg.doSeconds || !gamer.isAlive()) {
                event.setCancelled(true);
                if (entity.getFireTicks() > 0 && !pm.getGamer(entity).isAlive())
                    entity.setFireTicks(0);
            }
        } else if (entity instanceof Tameable && ((Tameable) entity).isTamed()
                && hg.currentTime <= config.getTimeForInvincibility())
            event.setCancelled(true);
    }

    @EventHandler
    public void onPotion(PotionSplashEvent event) {
        Iterator<LivingEntity> itel = event.getAffectedEntities().iterator();
        while (itel.hasNext()) {
            LivingEntity e = itel.next();
            Gamer gamer = pm.getGamer(e);
            if (gamer != null && !gamer.isAlive()) {
                itel.remove();
            }
        }
    }

    @EventHandler
    public void onServerPing(ServerListPingEvent event) {
        if (hg.currentTime >= 0)
            event.setMotd(cm.getGameStartedMotd());
        else
            event.setMotd(returnTime(hg.currentTime));
    }

    @EventHandler
    public void onSpawn(CreatureSpawnEvent event) {
        if (event.getEntityType() == EntityType.SLIME)
            event.setCancelled(true);
        else {
            if (hg.currentTime < 0) {
                if (event.getSpawnReason() != SpawnReason.CUSTOM) {
                    event.setCancelled(true);
                    if (!(event.getEntity() instanceof Monster)) {
                        if (config.getSpawnChanceMonster() <= 0 || new Random().nextInt(config.getSpawnChanceMonster()) == 0)
                            hg.entitysToSpawn.put(event.getLocation().clone().add(0, new Random().nextDouble(), 0),
                                    event.getEntityType());
                    }
                }
            } else if (event.getEntity() instanceof Animals || event.getEntity() instanceof NPC) {
                if (event.getSpawnReason() == SpawnReason.CHUNK_GEN || event.getSpawnReason() == SpawnReason.NATURAL) {
                    if (config.getSpawnChanceAnimal() > 0 && new Random().nextInt(config.getSpawnChanceAnimal()) != 0)
                        event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent event) {
        if (hg.currentTime < 0)
            event.setCancelled(true);
        else if (event.getTarget() instanceof Player) {
            Gamer gamer = pm.getGamer((Player) event.getTarget());
            if (gamer == null || !gamer.isAlive())
                event.setCancelled(true);
        }
    }

    @EventHandler
    public void pigZap(PigZapEvent event) {
        event.setCancelled(true);
    }

    private String returnTime(Integer i) {
        i = Math.abs(i);
        int remainder = i % 3600, minutes = remainder / 60, seconds = remainder % 60;
        if (seconds == 0 && minutes == 0)
            return cm.getTimeFormatNoTime();
        if (minutes == 0) {
            if (seconds == 1)
                return String.format(cm.getTimeFormatMotdSecond(), seconds);
            return String.format(cm.getTimeFormatMotdSeconds(), seconds);
        }
        if (seconds == 0) {
            if (minutes == 1)
                return String.format(cm.getTimeFormatMotdMinute(), minutes);
            return String.format(cm.getTimeFormatMotdMinutes(), minutes);
        }
        if (seconds == 1) {
            if (minutes == 1)
                return String.format(cm.getTimeFormatMotdSecondAndMinute(), minutes, seconds);
            return String.format(cm.getTimeFormatMotdSecondAndMinutes(), minutes, seconds);
        }
        if (minutes == 1) {
            return String.format(cm.getTimeFormatMotdSecondsAndMinute(), minutes, seconds);
        }
        return String.format(cm.getTimeFormatMotdSecondsAndMinutes(), minutes, seconds);
    }

}
