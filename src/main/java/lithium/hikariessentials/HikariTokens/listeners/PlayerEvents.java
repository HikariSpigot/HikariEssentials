package lithium.hikariessentials.HikariTokens.listeners;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.UserData;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerEvents implements Listener {


    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        UserData.createUserAccount(player);
        HikariEssentialsToken.getTokenManager(player);
        HikariEssentialsToken.getBankManager(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (HikariEssentialsToken.getTokenMap().containsKey(player)) {
            TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
            BankManager bank = HikariEssentialsToken.getBankManager(player);

            UserData.setTokens(player.getUniqueId(), tokens.getTokens());
            UserData.setBank(player.getUniqueId(), bank.getBank());

            HikariEssentialsToken.getTokenMap().remove(e.getPlayer());
            HikariEssentialsToken.getBankMap().remove(e.getPlayer());
        }
    }
    @EventHandler
    public void onAdvance(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariEssentialsToken.getTokenManager(player);

        if (HikariEssentialsToken.getConfigManager().isInDisabledWorld(player)) {
            if (HikariEssentialsToken.getConfigManager().getValueEnabled("advancement-complete")) {
                if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    int tokens = HikariEssentialsToken.getConfigManager().getValue("advancement-complete");

                    man.addTokens(tokens);

                    if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("ADVANCEMENT", "&a+" + tokens).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onNetherEnter(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariEssentialsToken.getTokenManager(player);

        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            String str = HikariEssentialsToken.getConfigManager().getConfig().getString("t.player.events.nether-portal.value");

            assert str != null;
            if (str.startsWith("-")) {
                str = str.replaceAll("-", "");

                int value = Integer.parseInt(str);

                man.removeTokens(value);

                if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                    player.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PORTAL-NETHER", "&c-" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                }
            } else {
                int value = Integer.parseInt(str);
                if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    man.addTokens(value);

                    if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PORTAL-NETHER", "&a+" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                    }
                }
            }
        } else {

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {

                String str = HikariEssentialsToken.getConfigManager().getConfig().getString("t.player.events.end-portal.value");

                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PORTAL-END", "&c-" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);
                    if (!(man.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariEssentialsToken.getConfigManager().isEventMessage()) {
                            player.sendMessage(HikariEssentialsToken.getConfigManager().getEventMessage("PORTAL-END", "&a+" + value).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }
}
