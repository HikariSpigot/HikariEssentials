package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.HikariTokens.utils.menu.menus.ExchangeMenu;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TExchange implements CommandExecutor {

    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);
    private final ConfigManager config = HikariEssentialsToken.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("texchange")) {
                if (player.hasPermission("te.exchange") || player.hasPermission("te.player")) {
                    // /tbalance - giving the player their balance
                    new ExchangeMenu(HikariEssentialsToken.getMenuUtil(player)).open();
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                            HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }

}
