package lithium.hikariessentials.HikariTokens.hook;

import lithium.hikariessentials.HikariTokens.HikariEssentialsToken;
import lithium.hikariessentials.HikariTokens.utils.api.VaultAPI;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;

import java.util.Collection;

public class LoadHook {


    public static Economy econ = null;
    public static Permission vaultPerm = null;

    @SuppressWarnings("ConstantConditions")
    public static void load() {
        econ = new VaultAPI();
        RegisteredServiceProvider<Permission> rsp = HikariEssentialsToken.getInstance().getServer().getServicesManager().getRegistration(Permission.class);
        if (rsp != null) {
            vaultPerm = rsp.getProvider();
        }

        HikariEssentialsToken.getInstance().getServer().getServicesManager().register(Economy.class, econ, HikariEssentialsToken.getInstance(), ServicePriority.Highest);

        if (HikariEssentialsToken.getInstance().getConfig().getBoolean("t.support.disable-essentials-eco")) {
            Collection<RegisteredServiceProvider<Economy>> econs = Bukkit.getPluginManager().getPlugin("Vault").getServer().getServicesManager().getRegistrations(Economy.class);
            for (RegisteredServiceProvider<Economy> econ : econs) {
                if (econ.getProvider().getName().equalsIgnoreCase("Essentials Economy")||
                        econ.getProvider().getName().equalsIgnoreCase("EssentialsX Economy")) {
                    HikariEssentialsToken.getInstance().getServer().getServicesManager().unregister(econ.getProvider());
                }
            }
        }

        Bukkit.getConsoleSender().sendMessage("[Vault] HikariToken: Hooked into vault.");
    }

    public static boolean loadcm() {
        RegisteredServiceProvider<Economy> rsp = HikariEssentialsToken.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return true;
    }

    public static void unload() {
        HikariEssentialsToken.getInstance().getServer().getServicesManager().unregister(econ);
    }
}
