package lithium.hikariessentials.HikariTokens.listeners;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
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
            TokenManager man = HikariEssentialsToken.getTokenManager(killer);

            if (HikariEssentialsToken.getConfigManager().isInDisabledWorld(killer)) {
                if (HikariEssentialsToken.getConfigManager().getValueEnabled("kill-players")) {
                    if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        int tokens = HikariEssentialsToken.getConfigManager().getValue("kill-players");

                        man.addTokens(tokens);

                        if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                            killer.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PLAYER-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Mob && e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();

            assert killer != null;

            TokenManager man = HikariEssentialsToken.getTokenManager(killer);
            if (!HikariEssentialsToken.getConfigManager().isInDisabledWorld(killer)) {
                for (String entity : Objects.requireNonNull(HikariEssentialsToken.getConfigManager().getConfig().getConfigurationSection("t.player.events.kill-entities")).getKeys(false)) {
                    if (entity.contains(e.getEntity().getName().toUpperCase())) {

                        if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                            int tokens = HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.events.kill-entities." + entity.toUpperCase() + ".value");

                            man.addTokens(tokens);

                            if (HikariEssentialsToken.getConfigManager().getConfig().getBoolean("t.player.events.enable-messages")) {
                                killer.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("ENTITY-KILL", "&a+" + tokens).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())
                                        .replaceAll("%entity%", e.getEntity().getName()));
                            }
                        }
                    }
                }
            }
        } else if (e.getEntity() instanceof Player) {
            Player victim = (Player) e.getEntity();

            String str = HikariEssentialsToken.getConfigManager().getConfig().getString("t.player.events.player-death.value");
            TokenManager man = HikariEssentialsToken.getTokenManager(victim);
            if (HikariEssentialsToken.getConfigManager().isInDisabledWorld(victim)) {
                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                        victim.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PLAYER-DEATH", "&c-" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);

                    if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                            victim.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PLAYER-DEATH", "&a+" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }

}
