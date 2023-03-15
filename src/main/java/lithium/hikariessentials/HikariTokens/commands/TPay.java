package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.H2UserData;
import lithium.hikariessentials.HikariTokens.data.MySQLUserData;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TPay implements CommandExecutor {


    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("pay")) {
                if(player.hasPermission("te.pay") ||  player.hasPermission("te.player")) {
                    if (args.length == 2) {
                        Player receiver = Bukkit.getPlayer(args[0]);
                        int amount1 = Integer.parseInt(args[1]);
                        double amount2 = Double.parseDouble(args[1]);

                        if (receiver != null) {
                            if (te.isMySQL()) {
                                if (!MySQLUserData.getIgnore(receiver.getUniqueId())) {
                                    TokenManager ptokens = HikariEssentialsToken.getTokenManager(player);
                                    TokenManager rtokens = HikariEssentialsToken.getTokenManager(receiver);
                                    if (!(rtokens.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                                        if (receiver != player) {
                                            if (amount1 >= HikariEssentialsToken.getConfigManager().getMinPay() || amount2 >= HikariEssentialsToken.getConfigManager().getMinPay()) {
                                                if (amount1 <= HikariEssentialsToken.getConfigManager().getMaxPay() || amount2 <= HikariEssentialsToken.getConfigManager().getMaxPay()) {
                                                    if (ptokens.getTokens() >= amount1) {
                                                        if (isInt(args[1])) {
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else if (ptokens.getTokens() >= amount2) {
                                                        if (isInt(args[1])) {
                                                            // UserData.addTokens(receiver.getUniqueId(), amount1);
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else {
                                                        player.sendMessage(ChatColor.RED + "Du hast genug Tokens!");
                                                    }
                                                } else {
                                                    int value = HikariEssentialsToken.getConfigManager().getMaxPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden."));
                                                }
                                            } else {
                                                int value = HikariEssentialsToken.getConfigManager().getMinPay();
                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &&#00fb9aTokens &7senden."));
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Du kannst nicht Tokens an dich selbst senden!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Dieser Spieler kann keine Tokens mehr aufnehmen.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Dieser Spieler akzeptiert keine Token sendung!");
                                }
                            } else if (te.isH2()) {
                                if (!H2UserData.getIgnore(receiver.getUniqueId())) {
                                    TokenManager ptokens = HikariEssentialsToken.getTokenManager(player);
                                    TokenManager rtokens = HikariEssentialsToken.getTokenManager(receiver);
                                    if (!(rtokens.getTokens() >= HikariEssentialsToken.getConfigManager().getConfig().getInt("t.player.max-balance"))) {
                                        if (receiver != player) {
                                            if (amount1 >= HikariEssentialsToken.getConfigManager().getMinPay() || amount2 >= HikariEssentialsToken.getConfigManager().getMinPay()) {
                                                if (amount1 <= HikariEssentialsToken.getConfigManager().getMaxPay() || amount2 <= HikariEssentialsToken.getConfigManager().getMaxPay()) {
                                                    if (ptokens.getTokens() >= amount1) {
                                                        if (isInt(args[1])) {
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else if (ptokens.getTokens() >= amount2) {
                                                        if (isInt(args[1])) {
                                                            // UserData.addTokens(receiver.getUniqueId(), amount1);
                                                            rtokens.addTokens(amount1);
                                                            ptokens.removeTokens(amount1);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount1 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount2 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else {
                                                        player.sendMessage(ChatColor.RED + "Du hast Genug Tokens!");
                                                    }
                                                } else {
                                                    int value = HikariEssentialsToken.getConfigManager().getMaxPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden."));
                                                }
                                            } else {
                                                int value = HikariEssentialsToken.getConfigManager().getMinPay();
                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &&#00fb9aTokens &7senden."));
                                            }
                                        } else {
                                            player.sendMessage(ChatColor.RED + "Du kannst nicht Tokens an dich selbst senden!");
                                        }
                                    } else {
                                        player.sendMessage(ChatColor.RED + "Dieser Spieler kann keine Tokens mehr aufnehmen.");
                                    }
                                } else {
                                    player.sendMessage(ChatColor.RED + "Dieser Spieler akzeptiert keine Token sendung!");
                                }
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht Online.");
                        }
                    } else {
                        player.sendMessage(ChatColor.RED + "Befehl: /pay <player> <amount>");
                    }
                } else {
                    player.sendMessage(TokenUtils.applyFormat(Objects.requireNonNull(
                            HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

    public boolean isInt(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isDouble(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
