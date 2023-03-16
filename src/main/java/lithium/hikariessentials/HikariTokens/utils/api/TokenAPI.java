package lithium.hikariessentials.HikariTokens.utils.api;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.data.UserData;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import org.bukkit.OfflinePlayer;

public class TokenAPI {




    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);

    public int getTokensInt(OfflinePlayer player) {
        if (!player.isOnline()) {
            UserData.getTokensInt(player.getUniqueId());
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            return tokens.getTokens();
        }
        return 0;
    }

    public double getTokensDouble(OfflinePlayer player) {
        if (!player.isOnline()) {
            UserData.getTokensDouble(player.getUniqueId());
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            return tokens.getTokens();
        }

        return 0.0;
    }

    public void setTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.setTokens(amount);
        }
    }

    public void setTokens(OfflinePlayer player, double amount) {
        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.setTokens((int) amount);
        }
    }

    public void addTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.addTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.addTokens(amount);
        }
    }

    public void addTokens(OfflinePlayer player, double amount) {

        if (!player.isOnline()) {
            UserData.addTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.addTokens((int) amount);
        }
    }

    public void removeTokens(OfflinePlayer player, int amount) {

        if (!player.isOnline()) {
            UserData.removeTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.removeTokens(amount);
        }
    }

    public void removeTokens(OfflinePlayer player, double amount) {

        if (!player.isOnline()) {
            UserData.removeTokens(player.getUniqueId(), amount);
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.removeTokens((int) amount);
        }
    }

    public void resetTokens(OfflinePlayer player) {

        if (!player.isOnline()) {
            UserData.setTokens(player.getUniqueId(), HikariMain.getConfigManager().getDefaultTokens());
        } else {
            TokenManager tokens = HikariMain.getTokenManager(player);
            tokens.setTokens(HikariMain.getConfigManager().getDefaultTokens());
        }
    }



}
