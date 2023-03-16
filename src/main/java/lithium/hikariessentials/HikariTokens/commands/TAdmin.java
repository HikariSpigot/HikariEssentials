package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.data.H2UserData;
import lithium.hikariessentials.HikariTokens.data.MySQLUserData;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.Objects;
import java.util.regex.Pattern;

public class TAdmin implements CommandExecutor {


    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);
    private final ConfigManager config = HikariMain.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (cmd.getName().equalsIgnoreCase("tadmin")) {
                // /tadmin give/set/remove <name> <amount>

                if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("reload")) {

                        try {
                            te.getConfig().load(te.getDataFolder() + "/config.yml");
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                        }

                        try {
                            HikariMain.messageConfig.load(HikariMain.messageFile);
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage("Couldn't save message.yml properly!");
                        }

                        try {
                            HikariMain.tokenExchangeConfig.load(HikariMain.tokenExchangeFile);
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage("Couldn't save tokenexchange.yml properly!");
                        }

                        try {
                            HikariMain.tokenTopConfig.load(HikariMain.tokenTopFile);
                        } catch (IOException | InvalidConfigurationException e) {
                            e.printStackTrace();
                            Bukkit.getConsoleSender().sendMessage("Couldn't save tokentop.yml properly!");
                        }

                        sender.sendMessage(HikariMain.getConfigManager().getReload().replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        return true;
                    } else if (args[0].equalsIgnoreCase("version")) {
                        sender.sendMessage(TokenUtils.applyFormat("&e&lADMIN &7You are using &ev" + te.getDescription().getVersion() + " &7of &e" + te.getDescription().getName()));
                        return true;
                    }
                }

                if (args.length == 3) {

                    String option = args[0];
                    OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[1]);
                    int amount1 = Integer.parseInt(args[2]);

                    if (option.equalsIgnoreCase("give")) {
                        if (!receiver.isOnline()) {
                            if (te.isMySQL()) {
                                MySQLUserData.addTokens(receiver.getUniqueId(), amount1);
                            } else if (te.isH2()) {
                                H2UserData.addTokens(receiver.getUniqueId(), amount1);
                            }
                        } else {
                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                            tokens.addTokens(amount1);
                        }

                        sender.sendMessage(ColorUtils.translateColorCodes("&#00fb9aGiven " + receiver.getName() + " &e" + amount1 + " &#00fb9aTokens."));
                        return true;
                    } else if (option.equalsIgnoreCase("set")) {
                        if (!receiver.isOnline()) {
                            if (te.isMySQL()) {
                                MySQLUserData.setTokens(receiver.getUniqueId(), amount1);
                            } else if (te.isH2()) {
                                H2UserData.setTokens(receiver.getUniqueId(), amount1);
                            }
                        } else {
                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                            tokens.setTokens(amount1);
                        }

                        sender.sendMessage(ColorUtils.translateColorCodes("&e" + receiver.getName() + "'s &7token balance has been set to &e" + amount1));
                        return true;
                    } else if (option.equalsIgnoreCase("remove")) {
                        if (!receiver.isOnline()) {
                            if (te.isMySQL()) {
                                MySQLUserData.removeTokens(receiver.getUniqueId(), amount1);
                            } else if (te.isH2()) {
                                H2UserData.removeTokens(receiver.getUniqueId(), amount1);
                            }
                        } else {
                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                            tokens.removeTokens(amount1);
                        }
                        sender.sendMessage(TokenUtils.applyFormat("&7Removed &e" + amount1 + "&7 tokens from &e" + receiver.getName() + "&7."));
                        return true;
                    } else {
                        sender.sendMessage(TokenUtils.applyFormat("&cUsage: &7/tadmin give/set/remove <player> <amount>"));
                        return true;
                    }
                }
            }
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("tadmin")) {
                if (player.hasPermission("te.admin")) {
                    // /tadmin give/set/remove <name> <amount>
                    if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("reload")) {

                            try {
                                te.getConfig().load(te.getDataFolder() + "/config.yml");
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                            }

                            try {
                                HikariMain.messageConfig.load(HikariMain.messageFile);
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                                Bukkit.getConsoleSender().sendMessage("Couldn't save message.yml properly!");
                            }

                            try {
                                HikariMain.tokenMenuConfig.load(HikariMain.tokenMenuFile);
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                                Bukkit.getConsoleSender().sendMessage("Couldn't save tokenmenu.yml properly!");
                            }

                            try {
                                HikariMain.tokenExchangeConfig.load(HikariMain.tokenExchangeFile);
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                                Bukkit.getConsoleSender().sendMessage("Couldn't save tokenexchange.yml properly!");
                            }

                            try {
                                HikariMain.tokenTopConfig.load(HikariMain.tokenTopFile);
                            } catch (IOException | InvalidConfigurationException e) {
                                e.printStackTrace();
                                Bukkit.getConsoleSender().sendMessage("Couldn't save tokentop.yml properly!");
                            }

                            player.sendMessage(HikariMain.getConfigManager().getReload().replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                            return true;
                        } else if (args[0].equalsIgnoreCase("version")) {
                            player.sendMessage(TokenUtils.applyFormat("&e&lADMIN &7You are using &ev" + te.getDescription().getVersion() + " &7of &e" + te.getDescription().getName()));
                            return true;
                        }
                    }

                    if (args.length == 4) {

                        String option = args[1];
                        OfflinePlayer receiver = Bukkit.getOfflinePlayer(args[2]);
                        int amount1 = Integer.parseInt(args[3]);

                        String pattern = "([0-9]*)\\.([0-9]*)";
                        String num = String.valueOf(amount1);
                        boolean match = Pattern.matches(pattern, num);

                        if (args[0].equalsIgnoreCase("tokens")) {
                            if (option.equalsIgnoreCase("give")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.addTokens(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.addTokens(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                                            tokens.addTokens(amount1);
                                        }

                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariMain&r &#00fb9aGiven " + receiver.getName() + " &e" + amount1 + " &7Tokens."));
                                    } else {
                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9aError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(ColorUtils.translateColorCodes("&#00fb9aError: &7Value format is not correct."));
                                    return true;
                                }
                            } else if (option.equalsIgnoreCase("set")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.setTokens(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.setTokens(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                                            tokens.setTokens(amount1);
                                        }

                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariMain&r &7" + receiver.getName() + "'s token balance has been set to &e" + amount1));
                                    } else {
                                        player.sendMessage(TokenUtils.applyFormat("&cError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(TokenUtils.applyFormat("&cError: &7Value format is not correct."));
                                    return true;
                                }
                            } else if (option.equalsIgnoreCase("remove")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.removeTokens(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.removeTokens(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            TokenManager tokens = HikariMain.getTokenManager(receiver);
                                            tokens.removeTokens(amount1);
                                        }
                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariMain&r Removed &e" + amount1 + "&#00fb9a tokens from &e" + receiver.getName() + "&#00fb9a."));
                                    } else {
                                        player.sendMessage(TokenUtils.applyFormat("&cError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(TokenUtils.applyFormat("&cUsage: &7/tadmin tokens give/set/remove <player> <amount>"));
                                    return true;
                                }
                            } else {
                                player.sendMessage(TokenUtils.applyFormat("&cError: &7Value format is not correct."));
                                return true;
                            }
                        } else if (args[0].equalsIgnoreCase("bank")) {
                            if (option.equalsIgnoreCase("give")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.addBank(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.addBank(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            BankManager bank = HikariMain.getBankManager(receiver);
                                            bank.addBank(amount1);
                                        }

                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariBank&r &#00fb9aGiven &e" + receiver.getName() + " &e" + amount1 + " &#00fb9aTokens."));
                                    } else {
                                        player.sendMessage(TokenUtils.applyFormat("&cError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(TokenUtils.applyFormat("&cError: &7Value format is not correct."));
                                    return true;
                                }
                            } else if (option.equalsIgnoreCase("set")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.setBank(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.setBank(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            BankManager bank = HikariMain.getBankManager(receiver);
                                            bank.setBank(amount1);
                                        }

                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariBank&r &e" + receiver.getName() + "'s &#00fb9abank balance has been set to &e" + amount1));
                                    } else {
                                        player.sendMessage(TokenUtils.applyFormat("&cError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(TokenUtils.applyFormat("&cError: &7Value format is not correct."));
                                    return true;
                                }
                            } else if (option.equalsIgnoreCase("remove")) {
                                if (!(match)) {
                                    if (!(amount1 < 0)) {
                                        if (!receiver.isOnline()) {
                                            if (te.isMySQL()) {
                                                MySQLUserData.removeBank(receiver.getUniqueId(), amount1);
                                            } else if (te.isH2()) {
                                                H2UserData.removeBank(receiver.getUniqueId(), amount1);
                                            }
                                        } else {
                                            BankManager bank = HikariMain.getBankManager(receiver);
                                            bank.removeBank(amount1);
                                        }
                                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&lHikariBank&r &#00fb9aRemoved &e" + amount1 + "&#00fb9a tokens from &e" + receiver.getName() + "&#00fb9a."));
                                    } else {
                                        player.sendMessage(TokenUtils.applyFormat("&cError: &7Value can't be negative."));
                                        return true;
                                    }
                                } else {
                                    player.sendMessage(TokenUtils.applyFormat("&cError: &7Value format is not correct."));
                                    return true;
                                }
                            } else {
                                player.sendMessage(TokenUtils.applyFormat("&cUsage: &7/tadmin bank give/set/remove <player> <amount>"));
                                return true;
                            }
                        } else {
                            player.sendMessage(TokenUtils.applyFormat("&cUsage: &7/tadmin bank give/set/remove <player> <amount>"));
                            return true;
                        }
                    } else {
                        for (String admin_help : HikariMain.getConfigManager().getMessages().getStringList("m.ADMIN-HELP")) {
                            player.sendMessage(TokenUtils.applyFormat(admin_help).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        }
                        return true;
                    }
                } else {
                    player.sendMessage(TokenUtils.applyFormat(Objects.requireNonNull(
                            HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

}
