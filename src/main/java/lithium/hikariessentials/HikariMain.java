package lithium.hikariessentials;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import org.bukkit.plugin.java.JavaPlugin;

public final class HikariMain extends JavaPlugin {

    public void onEnable() {

        try {
            Class.forName("lithium.hikariessentials.HikariTokens.HikariEssentialsToken");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void onDisable() {



    }

}
