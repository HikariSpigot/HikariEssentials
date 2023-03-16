package lithium.hikariessentials.HikariTokens.listeners;

import lithium.hikariessentials.HikariMain;
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


    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        UserData.createUserAccount(player);
        HikariMain.getTokenManager(player);
        HikariMain.getBankManager(player);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (HikariMain.getTokenMap().containsKey(player)) {
            TokenManager tokens = HikariMain.getTokenManager(player);
            BankManager bank = HikariMain.getBankManager(player);

            UserData.setTokens(player.getUniqueId(), tokens.getTokens());
            UserData.setBank(player.getUniqueId(), bank.getBank());

            HikariMain.getTokenMap().remove(e.getPlayer());
            HikariMain.getBankMap().remove(e.getPlayer());
        }
    }
    @EventHandler
    public void onAdvance(PlayerAdvancementDoneEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariMain.getTokenManager(player);

        if (HikariMain.getConfigManager().isInDisabledWorld(player)) {
            if (HikariMain.getConfigManager().getValueEnabled("advancement-complete")) {
                if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    int tokens = HikariMain.getConfigManager().getValue("advancement-complete");

                    man.addTokens(tokens);

                    if (HikariMain.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariMain.getConfigManager().getEventMessage("ADVANCEMENT", "&a+" + tokens).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onNetherEnter(PlayerTeleportEvent e) {
        Player player = e.getPlayer();

        TokenManager man = HikariMain.getTokenManager(player);

        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            String str = HikariMain.getConfigManager().getConfig().getString("t.player.events.nether-portal.value");

            assert str != null;
            if (str.startsWith("-")) {
                str = str.replaceAll("-", "");

                int value = Integer.parseInt(str);

                man.removeTokens(value);

                if (HikariMain.getConfigManager().isEventMessage()) {
                    player.sendMessage(HikariMain.getConfigManager().getEventMessage("PORTAL-NETHER", "&c-" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                }
            } else {
                int value = Integer.parseInt(str);
                if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                    man.addTokens(value);

                    if (HikariMain.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariMain.getConfigManager().getEventMessage("PORTAL-NETHER", "&a+" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                    }
                }
            }
        } else {

            if (e.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {

                String str = HikariMain.getConfigManager().getConfig().getString("t.player.events.end-portal.value");

                assert str != null;
                if (str.startsWith("-")) {
                    str = str.replaceAll("-", "");

                    int value = Integer.parseInt(str);

                    man.removeTokens(value);

                    if (HikariMain.getConfigManager().isEventMessage()) {
                        player.sendMessage(HikariMain.getConfigManager().getEventMessage("PORTAL-END", "&c-" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                    }
                } else {
                    int value = Integer.parseInt(str);
                    if (!(man.getTokens() >= HikariMain.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                        man.addTokens(value);

                        if (HikariMain.getConfigManager().isEventMessage()) {
                            player.sendMessage(HikariMain.getConfigManager().getEventMessage("PORTAL-END", "&a+" + value).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        }
                    }
                }
            }
        }
    }
}
