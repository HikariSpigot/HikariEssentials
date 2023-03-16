package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.manager.BankManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TBank implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("bank")) {
                if (player.hasPermission("te.bank") || player.hasPermission("te.player")) {
                    if (args.length == 0) {
                        BankManager bank = HikariMain.getBankManager(player);

                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariMain.getConfigManager().getMessages().getString("m.BANK-BALANCE")).replace("%tokens%",
                                String.valueOf(bank.getBank()).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()))));
                    } else if (args.length == 1) {
                        if (args[0].equalsIgnoreCase("help")) {
                            for (String bank_help : HikariMain.getConfigManager().getMessages().getStringList("m.BANK-HELP")) {
                                player.sendMessage(ColorUtils.translateColorCodes(bank_help).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                            }
                        } else {
                            for (String bank_help : HikariMain.getConfigManager().getMessages().getStringList("m.BANK-HELP")) {
                                player.sendMessage(ColorUtils.translateColorCodes(bank_help).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                            }
                        }
                    } else if (args.length == 2) {
                        BankManager bank = HikariMain.getBankManager(player);
                        TokenManager tokens = HikariMain.getTokenManager(player);
                        // tbank withdraw <amount>
                        // tbank deposit <amount>
                        if (args[0].equalsIgnoreCase("withdraw")) {
                            int amount = Integer.parseInt(args[1]);
                            // remove from bank and add to token balance
                            // check if they have enough bank balance

                            if (!(amount > HikariMain.getConfigManager().getMaxWithdraw())) {
                                if (!(amount < HikariMain.getConfigManager().getMinWithdraw())) {
                                    if (bank.getBank() >= amount) {
                                        bank.removeBank(amount);
                                        tokens.addTokens(amount);

                                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                                HikariMain.getConfigManager().getMessages().getString("m.BANK-WITHDRAW")).replaceAll("%amount%",
                                                String.valueOf(amount)).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                    } else {
                                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                                HikariMain.getConfigManager().getMessages().getString("m.NOT-ENOUGH-TO-WITHDRAW")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                    }
                                } else {
                                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                            HikariMain.getConfigManager().getMessages().getString("m.MIN-WITHDRAW")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                }
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariMain.getConfigManager().getMessages().getString("m.MAX-WITHDRAW")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                            }
                        }

                        if (args[0].equalsIgnoreCase("deposit")) {
                            int amount = Integer.parseInt(args[1]);
                            // add into the bank and remove from token balance
                            // check if they have enough token balance

                            if (!(amount > HikariMain.getConfigManager().getMaxDeposit())) {
                                if (!(amount < HikariMain.getConfigManager().getMinDeposit())) {
                                    if (tokens.getTokens() >= amount) {
                                        tokens.removeTokens(amount);
                                        bank.addBank(amount);

                                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                                HikariMain.getConfigManager().getMessages().getString("m.BANK-DEPOSIT")).replaceAll("%amount%",
                                                String.valueOf(amount)).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                    } else {
                                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                                HikariMain.getConfigManager().getMessages().getString("m.NOT-ENOUGH-TO-DEPOSIT")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                    }
                                } else {
                                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                            HikariMain.getConfigManager().getMessages().getString("m.MIN-DEPOSIT")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                                }
                            } else {
                                player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                        HikariMain.getConfigManager().getMessages().getString("m.MAX-DEPOSIT")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                            }
                        }
                    } else {
                        for (String bank_help : HikariMain.getConfigManager().getMessages().getStringList("m.BANK-HELP")) {
                            player.sendMessage(ColorUtils.translateColorCodes(bank_help).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()));
                        }
                    }
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                            HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }
}
