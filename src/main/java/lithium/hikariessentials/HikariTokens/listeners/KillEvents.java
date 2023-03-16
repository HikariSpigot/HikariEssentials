package lithium.hikariessentials.HikariTokens.listeners;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.Objects;

public class KillEvents implements Listener {

    @EventHandler
    public void onKill(EntityDeathEvent e) {

        if (e.getEntity() instanceof Player && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            assert killer != null;
            TokenManager man = HikariMain.getTokenManager(killer);

            if (HikariMain.getConfigManager().isInDisabledWorld(killer)) {
                if (HikariMain.getConfigManager().getValueEnabled("kill-players")) {
                    if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        int tokens = HikariMain.getConfigManager().getValue("kill-players");

                        man.addTokens(tokens);

                        if (HikariMain.getConfigManager().isEventMessage()) {
                            killer.sendMessage(HikariMain.getConfigManager().getEventMessage("PLAYER-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Mob && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            assert killer != null;

            TokenManager man = HikariMain.getTokenManager(killer);
            if (!HikariMain.getConfigManager().isInDisabledWorld(killer)) {
                for (String entity : Objects.requireNonNull(HikariMain.getConfigManager().getConfig().getConfigurationSection("t.player.events.kill-entities")).getKeys(false)) {
                    if (entity.contains(e.getEntity().getName().toUpperCase())) {

                        if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                            int tokens = HikariMain.getConfigManager().getConfig().getInt("t.player.events.kill-entities." + entity.toUpperCase() + ".value");

                            man.addTokens(tokens);

                            if (HikariMain.getConfigManager().getConfig().getBoolean("t.player.events.enable-messages")) {
                                killer.sendMessage(HikariMain.getConfigManager().getEventMessage("ENTITY-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())
                                        .replaceAll("%entity%", e.getEntity().getName()));
                            }
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity();

            String str = HikariMain.getConfigManager().getConfig().getString("t.player.events.player-death.value");
            TokenManager man = HikariMain.getTokenManager(victim);
            if (HikariMain.getConfigManager().isInDisabledWorld(victim)) {
                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariMain.getConfigManager().isEventMessage()) {
                        victim.sendMessage(HikariMain.getConfigManager().getEventMessage("PLAYER-DEATH", "&c-" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);

                    if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariMain.getConfigManager().isEventMessage()) {
                            victim.sendMessage(HikariMain.getConfigManager().getEventMessage("PLAYER-DEATH", "&a+" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }

}
