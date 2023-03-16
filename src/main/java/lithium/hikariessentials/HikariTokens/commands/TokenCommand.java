package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.UserData;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.HikariTokens.utils.menu.menus.TokenMenu;
import lithium.hikariessentials.HikariTokens.utils.menu.menus.ExchangeMenu;
import lithium.hikariessentials.HikariTokens.utils.menu.menus.TopMenu;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

import static lithium.hikariessentials.HikariTokens.utils.TokenUtils.msgPlayer;

public class TokenCommand implements CommandExecutor {

    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);
    private final ConfigManager config = HikariEssentialsToken.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            TokenManager tokens = HikariEssentialsToken.getTokenManager(player);

            if (cmd.getName().equalsIgnoreCase("hikaritoken")) {
                if (args.length == 0) {
                    if (player.hasPermission("te.mainmenu")) {
                        new TokenMenu(HikariEssentialsToken.getMenuUtil(player)).open();
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                    }
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("help")) {
                        msgPlayer(player, "&#00fb9a&l光TOKEN Commands&r",
                                "",
                                "&#00fb9a/tokens pay <user> <amount> &7- sende einem Spieler Tokens.",
                                "&#00fb9a/tokens balance &7- zeigt deinen Tokenstand an.",
                                "&#00fb9a/tokens bank &7- zeigt deinen Kontostand auf der Bank an.",
                                "&#00fb9a/tokens exchange &7- Öffnet das austausch menu",
                                "&#00fb9a/tokens top &7- Öffnet die Top-Liste",
                                "&#00fb9a/tokens stats &7- Siehe die Server Statistik",
                                "&#00fb9a/tokens toggle &7- Erlaube/Verbiete das erhalten von Tokens.",
                                "&#00fb9a/tokens help &7- zeigt diese Nachricht.",
                                "",
                                "&#00fb9a&l光TOKENBank Befehle&r",
                                "",
                                "&#00fb9a/bank &7- zeigt dein Kontostand auf der Bank an.",
                                "&#00fb9a/bank withdraw <amount> &7- hebt von deiner Bank etwas ab.",
                                "&#00fb9a/bank deposit <amount> &7- zahlt was auf deine Bank ein.",
                                "");
                    } else if (args[0].equalsIgnoreCase("top")) {
                        if (player.hasPermission("te.baltop") || player.hasPermission("te.player")) {
                            new TopMenu(HikariEssentialsToken.getMenuUtil(player)).open();
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else if (args[0].equalsIgnoreCase("toggle")) {
                        if (player.hasPermission("te.toggle")) {
                            if (UserData.getIgnore(player.getUniqueId())) {
                                UserData.setIgnore(player.getUniqueId(), false);
                                player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN &7Spieler können dir wieder Tokens senden!"));
                            } else {
                                UserData.setIgnore(player.getUniqueId(), true);
                                player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN &7Spieler können dir jetzt keine Tokens mehr senden!"));
                            }
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else if (args[0].equalsIgnoreCase("bank")) {
                        if (player.hasPermission("te.bank") || player.hasPermission("te.player")) {
                            BankManager bank = HikariEssentialsToken.getBankManager(player);

                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.BANK-BALANCE")).replaceAll("%tokens%",
                                    String.valueOf(bank.getBank()).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()))));
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else if (args[0].equalsIgnoreCase("balance")) {
                        if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {

                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                    String.valueOf(tokens.getTokens()).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix()))));
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else if (args[0].equalsIgnoreCase("exchange")) {
                        if (player.hasPermission("te.exchange") || player.hasPermission("te.player")) {
                            new ExchangeMenu(HikariEssentialsToken.getMenuUtil(player)).open();
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else if (args[0].equalsIgnoreCase("stats")) {
                        if (player.hasPermission("te.stats") || player.hasPermission("te.player")) {
                            player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKEN STATS &8&l>"));
                            player.sendMessage(ColorUtils.translateColorCodes("&7Server Total: &e" + UserData.getServerTotalTokens()));
                            player.sendMessage(ColorUtils.translateColorCodes("&7Dein Tokenstand: &e" + UserData.getTokensInt(player.getUniqueId())));
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falscher Befehl! Nutze &c/tokens help"));
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("pay")) {
                        if (player.hasPermission("te.pay") || player.hasPermission("te.player")) {
                            Player receiver = Bukkit.getPlayer(args[1]);
                            int amount1 = Integer.parseInt(args[2]);
                            double amount2 = Double.parseDouble(args[2]);
                            if (receiver != null) {
                                if (!UserData.getIgnore(receiver.getUniqueId())) {
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
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        } else if (isDouble(args[1])) {
                                                            rtokens.addTokens((int) amount2);
                                                            ptokens.removeTokens((int) amount2);
                                                            // send player & receiver confirmation message
                                                            player.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + receiver.getName() + amount1 + " &#00fb9aTokens &7gesendet."));
                                                            receiver.sendMessage(ColorUtils.translateColorCodes("&7Du hast &e" + amount2 + " &#00fb9aTokens &7von &e" + player.getName() + " &7erhalten!"));
                                                        }
                                                    } else {
                                                        player.sendMessage(ChatColor.RED + "Du hast nicht genug Tokens!");
                                                    }
                                                } else {
                                                    int value = HikariEssentialsToken.getConfigManager().getMaxPay();
                                                    player.sendMessage(ColorUtils.translateColorCodes("&7Du kannst Maximal &c" + value + " &#00fb9aTokens &7senden!"));
                                                }
                                            } else {
                                                int value = HikariEssentialsToken.getConfigManager().getMinPay();
                                                player.sendMessage(ColorUtils.translateColorCodes("&7Du musst minimal &c" + value + " &#00fb9aTokens &7senden!"));
                                            }
                                        } else {
                                            player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Du kannst nicht Tokens an dich selber senden!"));
                                        }
                                    } else {
                                        player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Dieser Spieler kann keine weiteren Token erhalten!"));
                                    }
                                } else {
                                    player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Du kannst keine Tokens an diesen Spieler senden!"));
                                }
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Dieser Spieler ist nicht Online!"));
                            }
                        } else {
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                        }
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falscher Befehl! Nutze &c/tokens help"));
                    }
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes("&c[&l!&c] &7Falsches Format! Use &c/tokens help"));
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
