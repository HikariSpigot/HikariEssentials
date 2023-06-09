package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.HikariTokens.manager.TokenManager;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TBalance implements CommandExecutor {


    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);
    private final ConfigManager config = HikariMain.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            // if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {

            TokenManager tokens = HikariMain.getTokenManager(player);

            if (cmd.getName().equalsIgnoreCase("balance")) {
                // /tbalance - giving the player their balance
                if (args.length == 1) {
                    if (player.hasPermission("te.balance.other")) {
                        Player target = Bukkit.getPlayer(args[0]);

                        if (target != null) {
                            TokenManager tokensTarget = HikariMain.getTokenManager(player);
                            player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                    HikariMain.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                    String.valueOf(tokensTarget.getTokens()).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()))));
                        }
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                    }
                } else {
                    if (player.hasPermission("te.balance") || player.hasPermission("te.player")) {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariMain.getConfigManager().getMessages().getString("m.BALANCE")).replaceAll("%tokens%",
                                String.valueOf(tokens.getTokens()).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix()))));
                    } else {
                        player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                                HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                    }
                }
            }
        }
        return false;
    }

}
