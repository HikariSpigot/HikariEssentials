package lithium.hikariessentials.HikariTokens.commands;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.data.UserData;
import lithium.hikariessentials.HikariTokens.manager.ConfigManager;
import lithium.hikariessentials.Utils.ColorUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class TToggle implements CommandExecutor {

    private final HikariEssentialsToken te = HikariEssentialsToken.getPlugin(HikariEssentialsToken.class);
    private final ConfigManager config = HikariEssentialsToken.getConfigManager();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {

            Player player = (Player) sender;

            if (cmd.getName().equalsIgnoreCase("toggle")) {
                if (player.hasPermission("te.toggle")) {
                    if (UserData.getIgnore(player.getUniqueId())) {
                        UserData.setIgnore(player.getUniqueId(), false);
                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKENS &7Spieler können dir wieder &#00fb9aTokens &7senden!"));
                    } else {
                        UserData.setIgnore(player.getUniqueId(), true);
                        player.sendMessage(ColorUtils.translateColorCodes("&#00fb9a&l光TOKENS &7Spieler können dir keine &#00fb9aTokens &7mehr senden!"));
                    }
                } else {
                    player.sendMessage(ColorUtils.translateColorCodes(Objects.requireNonNull(
                            HikariEssentialsToken.getConfigManager().getMessages().getString("m.PERMISSION")).replaceAll("%PREFIX%", HikariEssentialsToken.getConfigManager().getPrefix())));
                }
            }
        }
        return false;
    }


}
