package lithium.hikariessentials.HikariTokens.hook;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.H2UserData;
import lithium.hikariessentials.HikariTokens.data.MySQLUserData;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.List;
import java.util.Objects;

public class VaultAPI implements Economy {


    @Override
    public boolean isEnabled() {
        return HikariEssentialsToken.getInstance().getConfig().getBoolean("t.support.tokeneco-vault-dependency");
    }

    @Override
    public String getName() {
        return HikariEssentialsToken.getInstance().getDescription().getName();
    }

    @Override
    public boolean hasBankSupport() {
        return false;
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public String format(double amount) {
        return null;
    }

    @Override
    public String currencyNamePlural() {
        return null;
    }

    @Override
    public String currencyNameSingular() {
        return null;
    }

    @Override
    public boolean hasAccount(String playerName) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (HikariEssentialsToken.getInstance().isMySQL()) {
            return HikariEssentialsToken.getUser().exists(player.getUniqueId());
        } else if (HikariEssentialsToken.getInstance().isH2()) {
            return HikariEssentialsToken.getH2user().exists(player.getUniqueId());
        }

        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player) {
        if (HikariEssentialsToken.getInstance().isMySQL()) {
            return HikariEssentialsToken.getUser().exists(player.getUniqueId());
        } else if (HikariEssentialsToken.getInstance().isH2()) {
            return HikariEssentialsToken.getH2user().exists(player.getUniqueId());
        }

        return false;
    }

    @Override
    public boolean hasAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean hasAccount(OfflinePlayer player, String worldName) {
        return false;
    }

    @Override
    public double getBalance(String playerName) {
        double amount = 0;

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        if (!player.isOnline()) {
            if (HikariEssentialsToken.getInstance().isMySQL()) {
                amount = MySQLUserData.getTokensInt(player.getUniqueId());
            } else if (HikariEssentialsToken.getInstance().isMySQL()) {
                amount = H2UserData.getTokensInt(player.getUniqueId());
            }
        } else {
            TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
            amount = tokens.getTokens();
        }

        return amount;
    }

    @Override
    public double getBalance(OfflinePlayer player) {
        double amount = 0;

        if (!player.isOnline()) {
            if (HikariEssentialsToken.getInstance().isMySQL()) {
                amount = MySQLUserData.getTokensInt(player.getUniqueId());
            } else if (HikariEssentialsToken.getInstance().isMySQL()) {
                amount = H2UserData.getTokensInt(player.getUniqueId());
            }
        } else {
            TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
            amount = tokens.getTokens();
        }

        return amount;
    }

    @Override
    public double getBalance(String playerName, String world) {
        return 0;
    }

    @Override
    public double getBalance(OfflinePlayer player, String world) {
        return 0;
    }

    @Override
    public boolean has(String playerName, double amount) {

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
        return tokens.getTokens() >= amount;
    }

    @Override
    public boolean has(OfflinePlayer player, double amount) {
        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
        return tokens.getTokens() >= amount;
    }

    @Override
    public boolean has(String playerName, String worldName, double amount) {
        return false;
    }

    @Override
    public boolean has(OfflinePlayer player, String worldName, double amount) {
        return false;
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, double amount) {
        int i = (int) amount;

        OfflinePlayer player = Bukkit.getOfflinePlayer(playerName);

        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);


        if (tokens.getTokens() < amount) {
            return new EconomyResponse(0.0D, tokens.getTokens(), EconomyResponse.ResponseType.FAILURE, "Insufficient balance!");
        }

        tokens.removeTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, double amount) {

        int i = (int) amount;

        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);


        if (tokens.getTokens() < amount) {
            return new EconomyResponse(0.0D, tokens.getTokens(), EconomyResponse.ResponseType.FAILURE, "Insufficient balance!");
        }

        tokens.removeTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse withdrawPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer player, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, double amount) {
        int i = (int) amount;

        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
        tokens.addTokens(i);

        return new EconomyResponse(amount, tokens.getTokens(), EconomyResponse.ResponseType.SUCCESS, "");
    }

    @Override
    public EconomyResponse depositPlayer(String playerName, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer player, String worldName, double amount) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, String player) {
        return null;
    }

    @Override
    public EconomyResponse createBank(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse deleteBank(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankBalance(String name) {
        return null;
    }

    @Override
    public EconomyResponse bankHas(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankWithdraw(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse bankDeposit(String name, double amount) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, String playerName) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String name, OfflinePlayer player) {
        return null;
    }

    @Override
    public List<String> getBanks() {
        return null;
    }

    @Override
    public boolean createPlayerAccount(String playerName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player) {
        if (HikariEssentialsToken.getInstance().isMySQL()) {
            HikariEssentialsToken.getUser().createPlayer(Objects.requireNonNull(player.getPlayer()));
            HikariEssentialsToken.getTokenManager(player);
            HikariEssentialsToken.getBankManager(player);
        } else if (HikariEssentialsToken.getInstance().isH2()) {
            HikariEssentialsToken.getH2user().createPlayer(Objects.requireNonNull(player.getPlayer()));
            HikariEssentialsToken.getTokenManager(player);
            HikariEssentialsToken.getBankManager(player);
        }

        return false;
    }

    @Override
    public boolean createPlayerAccount(String playerName, String worldName) {
        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer player, String worldName) {
        return false;
    }
}
