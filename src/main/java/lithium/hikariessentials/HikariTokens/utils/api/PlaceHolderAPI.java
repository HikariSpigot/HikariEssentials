package lithium.hikariessentials.HikariTokens.utils.api;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.UserData;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceHolderAPI extends PlaceholderExpansion {


    private final HikariEssentialsToken plugin;

    public PlaceHolderAPI(HikariEssentialsToken plugin) {
        this.plugin = plugin;
    }

    @Override
    public @NotNull String getAuthor() {
        return "noscape";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "te";
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if (params.equalsIgnoreCase(player.getName() + "_money")) {
            String text = null;

            if (player.isOnline()) {
                TokenManager man = HikariEssentialsToken.getTokenManager(player);
                text = String.valueOf(man.getTokens());
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_bank")) {
            String text = null;

            if (player.isOnline()) {
                BankManager bank = HikariEssentialsToken.getBankManager(player);
                text = String.valueOf(bank.getBank());
            } else {
                text = String.valueOf(UserData.getBankInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_money_formatted")) {
            String text = null;

            if (player.isOnline()) {
                TokenManager man = HikariEssentialsToken.getTokenManager(player);
                text = HikariEssentialsToken.getConfigManager().getTokenSymbol() + man.getTokens();
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase(player.getName() + "_bank_formatted")) {
            String text = null;

            if (player.isOnline()) {
                BankManager bank = HikariEssentialsToken.getBankManager(player);
                text = HikariEssentialsToken.getConfigManager().getTokenSymbol() + bank.getBank();
            } else {
                text = String.valueOf(UserData.getTokensInt(player.getUniqueId()));
            }

            return text;
        }

        if (params.equalsIgnoreCase("player_bank")) {
            String text;

            BankManager bank = HikariEssentialsToken.getBankManager(player);
            text = String.valueOf(bank.getBank());

            return text;
        }

        if (params.equalsIgnoreCase("player_money")) {
            String text;

            TokenManager man = HikariEssentialsToken.getTokenManager(player);
            text = String.valueOf(man.getTokens());

            return text;
        }

        if (params.equalsIgnoreCase("player_money_formatted")) {
            String text;

            TokenManager man = HikariEssentialsToken.getTokenManager(player);
            text = HikariEssentialsToken.getConfigManager().getTokenSymbol() + man.getTokens();

            return text;
        }

        return null;
    }

}