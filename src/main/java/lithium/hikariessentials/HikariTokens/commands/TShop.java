package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariMain;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.HikariTokens.utils.TokenUtils;
import lithium.hikariessentials.HikariTokens.utils.menu.menus.TokenShop;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TShop implements CommandExecutor {

    private final HikariMain te = HikariMain.getPlugin(HikariMain.class);
    private final ConfigManager config = HikariMain.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("tshop")) {
                if (player.hasPermission("te.shop") || player.hasPermission("te.player")) {
                    // /tbalance - giving the player their balance
                    new TokenShop(HikariMain.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(TokenUtils.applyFormat(Objects.requireNonNull(
                            HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                }
            } else if (cmd.getName().equalsIgnoreCase("shop")) {
                if (player.hasPermission("te.shop") || player.hasPermission("te.player")) {
                    // /tbalance - giving the player their balance
                    new TokenShop(HikariMain.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(TokenUtils.applyFormat(Objects.requireNonNull(
                            HikariMain.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariMain.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

}
