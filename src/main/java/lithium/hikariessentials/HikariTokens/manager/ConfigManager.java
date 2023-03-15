package lithium.hikariessentials.HikariTokens.manager;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.H2UserData;
import lithium.hikariessentials.HikariTokens.data.MySQLUserData;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ConfigManager {


    private final FileConfiguration config;
    private final FileConfiguration messages;
    private final FileConfiguration tokenexchange;
    private final FileConfiguration tokentop;
    private final FileConfiguration tokenmenu;

    public ConfigManager(FileConfiguration config, FileConfiguration messages, FileConfiguration tokenexchange, FileConfiguration tokentop, FileConfiguration tokenmenu) {
        this.config = config;
        this.messages = messages;
        this.tokenexchange = tokenexchange;
        this.tokentop = tokentop;
        this.tokenmenu = tokenmenu;
    }

    public String getTokenSymbol() {
        return TokenUtils.applyFormat(getMessages().getString("m.SYMBOL"));
    }

    public String getBalanceSQL(Player player) {
        return TokenUtils.applyFormat(Objects.requireNonNull(this.getMessages().getString("m.BALANCE")).replaceAll("%tokens%", String.valueOf(MySQLUserData.getTokensDouble(player.getUniqueId()))));
    }

    public String getBalanceH2(Player player) {
        return TokenUtils.applyFormat(Objects.requireNonNull(this.getMessages().getString("m.BALANCE")).replaceAll("%tokens%", String.valueOf(H2UserData.getTokensDouble(player.getUniqueId()))));
    }

    public boolean isInDisabledWorld(Player player) {
        if (getDisabledWorlds() != null)
            for (String world : getDisabledWorlds())
                if (Objects.requireNonNull(player.getLocation().getWorld()).getName().equalsIgnoreCase(world))
                    return true;

        return false;
    }

    public List<String> getMythicMobs() {
        return new ArrayList<>(Objects.requireNonNull(getConfig().getConfigurationSection("t.player.events.mythic-mobs")).getKeys(false));
    }

    public List<String> getMobs() {
        return new ArrayList<>(Objects.requireNonNull(getConfig().getConfigurationSection("t.player.events.kill-mobs")).getKeys(false));
    }

    public List<String> getAnimals() {
        return new ArrayList<>(Objects.requireNonNull(getConfig().getConfigurationSection("t.player.events.kill-animals")).getKeys(false));
    }

    public List<String> getDisabledWorlds() {
        return getConfig().getStringList("t.restrictions.disabled-worlds");
    }
    public List<String> getDisabledRegions() {
        return getConfig().getStringList("t.restrictions.disabled-regions");
    }

    public String getPay() {
        return TokenUtils.applyFormat(getMessages().getString("m.PAY"));
    }

    public String getEventMessage(String str, String tokens) {
        return TokenUtils.applyFormat(Objects.requireNonNull(getMessages().getString("m." + str)).replaceAll("%tokens%", tokens));
    }

    public String getReload() {
        return TokenUtils.applyFormat(getMessages().getString("m.RELOADED"));
    }

    public String getPrefix() {
        return TokenUtils.applyFormat(getMessages().getString("m.PREFIX"));
    }

    public int getDefaultTokens() {
        return getConfig().getInt("t.player.starting-balance");
    }

    public int getDefaultBank() {
        return getConfig().getInt("t.player.bank.starting-balance");
    }

    public int getMaxBank() {
        return getConfig().getInt("t.player.bank.max-bank");
    }

    public int getMinDeposit() {
        return getConfig().getInt("t.player.bank.min-bank-deposit");
    }

    public int getMaxDeposit() {
        return getConfig().getInt("t.player.bank.max-bank-deposit");
    }

    public int getMinWithdraw() {
        return getConfig().getInt("t.player.bank.min-bank-withdraw");
    }

    public int getMaxWithdraw() {
        return getConfig().getInt("t.player.bank.max-bank-withdraw");
    }

    public boolean isEventMessage() {
        return getConfig().getBoolean("t.player.events.enable-messages");
    }

    public int getValue(String str) {
        return getConfig().getInt("t.player.events." + str + ".value");
    }

    public boolean isConfirmPay() {
        return getConfig().getBoolean("t.player.confirm-pay");
    }

    public boolean getValueEnabled(String str) {
        return getConfig().getInt("t.player.events." + str + ".value") != 0;
    }

    public boolean getValueMob(String str) {
        return getConfig().getInt("t.player.events.kill-mobs." + str + ".value") != 0;
    }

    public boolean getValueAnimals(String str) {
        return getConfig().getInt("t.player.events.kill-animals." + str + ".value") != 0;
    }

    public boolean getValueBoolean(String str) {
        return getConfig().getBoolean("t.support." + str);
    }

    public String getTitleShop() {
        return getTokenExchange().getString("gui.title");
    }

    public String getTitleTop() {
        return getTokenTop().getString("gui.title");
    }

    public String getTitleMenu(Player player) {
        TokenManager tokens = HikariEssentialsToken.getTokenManager(player);
        return Objects.requireNonNull(getTokenMenu().getString("gui.title")).replaceAll("%tokens%", String.valueOf(tokens.getTokens()));
    }

    public int getSlotsShop() {
        return getTokenExchange().getInt("gui.slots");
    }

    public int getSlotsTop() {
        return getTokenTop().getInt("gui.slots");
    }

    public int getSlotsMenu() {
        return getTokenMenu().getInt("gui.slots");
    }

    public boolean isBankBalanceShop() {
        return getConfig().getBoolean("t.player.bank.exchange-bank-balance");
    }

    public boolean isCommandCosts() {
        return getConfig().getBoolean("t.plugin.command-cost");
    }

    public boolean isTokenPay() {
        return getConfig().getBoolean("t.plugin.token-pay");
    }

    public HashMap<String, Integer> getCommandsCosts() {
        HashMap<String, Integer> commands = new HashMap<>();

        for (String cmds : Objects.requireNonNull(getConfig().getConfigurationSection("t.player.command-costs")).getKeys(false)) {
            int value = getConfig().getInt("t.player.commands-costs." + cmds + ".value");

            commands.put(cmds, value);
        }

        return commands;
    }

    public int getMinPay() {
        return getConfig().getInt("t.player.min-pay");
    }

    public int getMaxPay() {
        return getConfig().getInt("t.player.max-pay");
    }

    public List<String> getItems() {
        List<String> itemNames = new ArrayList<>();
        for (String items : Objects.requireNonNull(getTokenExchange().getConfigurationSection("gui.items")).getKeys(false))
            itemNames.add(items);

        return itemNames;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public FileConfiguration getMessages() {
        return messages;
    }

    public FileConfiguration getTokenExchange() {
        return tokenexchange;
    }

    public FileConfiguration getTokenTop() {
        return tokentop;
    }

    public FileConfiguration getTokenMenu() {
        return tokenmenu;
    }

}
